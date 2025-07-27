package OrangeHR;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;

import com.aventstack.extentreports.*;
import utils.ExcelUtils;
import utils.ExtentReport;

import java.time.Duration;
import java.util.List;

public class OrangeHRMEmployeeSearchTest {
    WebDriver driver;
    WebDriverWait wait;
    ExtentReports report;
    ExtentTest test;

    @BeforeTest
    public void setupReport() {
        report = ExtentReport.getReportInstance();
    }

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type='submit']")));

        usernameField.sendKeys("Admin");
        passwordField.sendKeys("admin123");
        loginButton.click();
    }

    @Test
    public void searchEmployeesFromExcel() {
        ExcelUtils excel = new ExcelUtils("src/test/resources/EmployeeData.xlsx", "Search");
        List<String> employeeNames = excel.getEmployeeNames(0);  // 0 = first column

        for (String empName : employeeNames) {
            test = report.createTest("Search Employee: " + empName);

            try {
                // Click Admin tab
                WebElement adminTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Admin']")));
                adminTab.click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='System Users']")));

                // Enter employee name
                WebElement employeeNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[text()='Employee Name']/../following-sibling::div//input\"")));
                employeeNameField.clear();
                employeeNameField.sendKeys(empName);

                // Click Search
                WebElement searchButton = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]"));
                searchButton.click();

                // Wait for results to load
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='table']")));

                // Check if employee name appears
                if (driver.getPageSource().contains(empName)) {
                    test.pass(empName + " found in search results.");
                } else {
                    test.fail(empName + " NOT found in search results.");
                }

            } catch (Exception e) {
                test.fail("Exception occurred while searching: " + e.getMessage());
            }
        }
        report.flush(); // this can also be placed in @AfterTest
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterTest
    public void flushReport() {
        report.flush();
    }
}
