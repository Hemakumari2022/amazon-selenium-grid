package OrangeHR;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import org.testng.Assert;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class OrangeHRMLoginTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;

    @BeforeTest
    public void setupReport() {
        ExtentSparkReporter spark = new ExtentSparkReporter("OrangeHRM_Report.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) throws Exception {
        test = extent.createTest("Login Test - Username: " + username);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
            WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));

            usernameField.sendKeys(username);
            passwordField.sendKeys(password);
            loginButton.click();

            Thread.sleep(2000); // wait for page transition

            takeScreenshot(username + "_" + password);
// Login success
            boolean loginSuccess = false;
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[1]/span/h6")));
                loginSuccess = true;
            } catch (TimeoutException e) {
                System.out.println("Dashboard not found. Login might have failed for: " + username);
            }

            if (username.equals("Admin") && password.equals("admin123")) {
                Assert.assertTrue(loginSuccess, "Expected login to succeed");
            } else {
                Assert.assertFalse(loginSuccess, "Expected login to fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }

    
    @Test
    public void checkFileExists() {
        String filePath = "D:\\Automation\\AmazonSeleniumGridProject\\amazontest12\\LoginData.xlsx";
        File file = new File(filePath);

        if (file.exists()) {
            System.out.println("✅ File exists: " + filePath);
        } else {
            System.out.println("❌ File NOT found at: " + filePath);
        }
    }
    public void logout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.className("oxd-userdropdown-name")));
        dropdown.click();
        WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Logout']")));
        logoutButton.click();
    }

    public void takeScreenshot(String name) throws IOException {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path dest = Paths.get("screenshots", name + ".png");
        Files.createDirectories(dest.getParent());
        Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
    }

    @DataProvider(name = "loginData")
    public Object[][] dataProvider() throws IOException {
        String excelPath = Paths.get(System.getProperty("user.dir"), "LoginData.xlsx").toString();
        return getExcelData(excelPath, "Sheet1");
    }

    public Object[][] getExcelData(String filePath, String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();
        Object[][] data = new Object[rowCount - 1][colCount];

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < colCount; j++) {
                data[i - 1][j] = row.getCell(j).toString();
            }
        }
        workbook.close();
        return data;
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @AfterTest
    public void flushReport() {
        extent.flush();
    }
}
