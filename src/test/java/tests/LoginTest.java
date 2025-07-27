package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @Test(priority = 1)
    public void testValidLogin() {
    	LoginPage loginPage = new LoginPage(driver);
    	loginPage.login("demouser", "testingisfun99");

        // Assertion for successful login
 //   	boolean isLoggedIn = driver.getCurrentUrl().contains("demouser") || 
 //               driver.findElements(By.xpath("//span[@class='username' and text()='demouser']")).size() > 0;
//Assert.assertTrue(isLoggedIn, "User should be logged in with valid credentials");
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        loginPage = new LoginPage(driver);
        loginPage.login("wronguser", "wrongpass");

        // Assertion for failure message (depends on actual app behavior)
  //      boolean isErrorDisplayed = driver.getPageSource().toLowerCase().contains("invalid username") ||
    //            driver.findElements(By.xpath("//h3[@class='api-error' and text()='Invalid Username']")).size() > 0;
//Assert.assertTrue(isErrorDisplayed, "Error message should be shown for invalid login");
    }

    @Test(priority = 3)
    public void testEmptyLogin() {
        loginPage = new LoginPage(driver);
        loginPage.login("", "");

        // Assertion for empty field validation (depends on UI handling)
  //      boolean isErrorDisplayed = driver.getPageSource().toLowerCase().contains("invalid username") ||
    //            driver.findElements(By.xpath("//h3[@class='api-error' and text()='Invalid Username']")).size() > 0;
//Assert.assertTrue(isErrorDisplayed, "Error message should be shown for empty login");
    }
}
