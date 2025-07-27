package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.AdminPage;

import java.time.Duration;

public class OrangeHRMTests {

    WebDriver driver;
    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Initialize page objects
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
    }

    @Test(priority = 1)
    public void testLogin() {
        loginPage.login("Admin", "admin123");
    }

    @Test(priority = 2)
    public void testSidebarOptionsCount() {
        adminPage.goToAdminTab();
        int count = adminPage.getSidebarOptionsCount();
        System.out.println("Total sidebar options: " + count);
        adminPage.printSidebarOptions();
        assert count == 12 : "Expected 12 options, but found: " + count;
    }

    @Test(priority = 3)
    public void testSearchByUsername() {
        adminPage.goToAdminTab();
        adminPage.searchByUsername("Admin");
    }

    @Test(priority = 4)
    public void testSearchByUserRole() {
        adminPage.goToAdminTab();
        adminPage.searchByUserRole("Admin"); // or "ESS"
    }

    @Test(priority = 5)
    public void testSearchByStatus() {
        adminPage.goToAdminTab();
        adminPage.searchByUserStatus("Enabled"); // or "Disabled"
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
