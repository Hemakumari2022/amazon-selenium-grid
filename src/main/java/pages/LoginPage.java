package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import utils.WaitUtils;

public class LoginPage {
	private  WebDriver driver;

    // Locators
    private By loginLink = By.id("signin");

    private By usernameDropdown = By.xpath("//div[@id='username']//div[contains(@class,'css-1wa3eu0-placeholder')]");
    private By usernameOption(String username) {
        return By.xpath("//div[@id='username']//div[text()='" + username + "']");
    }

    private By passwordDropdown = By.xpath("//div[@id='password']//div[contains(@class,'css-1wa3eu0-placeholder')]");
    private By passwordOption(String password) {
        return By.xpath("//div[@id='password']//div[text()='" + password + "']");
    }

    // ✅ Updated login button locator (as requested)
    private By loginButton = By.id("login-btn");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openLoginPopup() {
        driver.findElement(loginLink).click();
    }

    public void selectUsername(String username) {
        WaitUtils.waitForElementVisible(driver, usernameDropdown, 10);
        driver.findElement(usernameDropdown).click();

        // Type the username
        WebElement input = driver.switchTo().activeElement();
        input.sendKeys(username);

        try {
            // Wait for dropdown to appear and select the first matching option
            By firstOption = By.cssSelector("div[class*='option']");
            WaitUtils.waitForElementVisible(driver, firstOption, 5);
            driver.findElement(firstOption).click();
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            System.out.println("No username suggestion appeared for input: " + username);
            driver.findElement(By.tagName("body")).click(); // dismiss dropdown
        }
    }

    public void selectPassword(String password) {
        WaitUtils.waitForElementVisible(driver, passwordDropdown, 10);
        driver.findElement(passwordDropdown).click();

        // Type the password
        WebElement input = driver.switchTo().activeElement();
        input.sendKeys(password);

        try {
            // Wait for dropdown to appear and select the first matching option
            By firstOption = By.cssSelector("div[class*='option']");
            WaitUtils.waitForElementVisible(driver, firstOption, 5);
            driver.findElement(firstOption).click();
        } catch (NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            System.out.println("No password suggestion appeared for input: " + password);
            driver.findElement(By.tagName("body")).click(); // dismiss dropdown
        }
    }
    public void clickLoginButton() {
        WaitUtils.waitForElementVisible(driver, loginButton, 5);
        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {
        openLoginPopup();
        if (!username.isEmpty()) {
            selectUsername(username);
        }
        if (!password.isEmpty()) {
            selectPassword(password);
        }
        clickLoginButton();
    }
}
