package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AdminPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Constructor
    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // Locators
    private By sidebarOptions = By.xpath("//div[@class='oxd-sidepanel-body']//ul/li/a/span");
    private By adminTab = By.xpath("//span[text()='Admin']");
    private By usernameField = By.xpath("//label[text()='Username']/following::input[1]");
    private By searchButton = By.xpath("//button[@type='submit']");
    private By resetButton = By.xpath("/html/body/div/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[1]");
    private By resultsTable = By.xpath("//div[@class='oxd-table-body']");

    private By roleDropdown = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')][1]");
    private By statusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]");

    private By roleOption(String role) {
        return By.xpath("//div[@role='option']//span[text()='" + role + "']");
    }

    private By statusOption(String status) {
        return By.xpath("//div[@role='option']//span[text()='" + status + "']");
    }

    // Methods

    // Click on Admin tab
    public void goToAdminTab() {
        wait.until(ExpectedConditions.elementToBeClickable(adminTab)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
    }

    // 1. Get all sidebar menu options
    public int getSidebarOptionsCount() {
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(sidebarOptions));
        return options.size();
    }

    public void printSidebarOptions() {
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(sidebarOptions));
        for (WebElement option : options) {
            System.out.println(option.getText());
        }
    }

    // 2. Search by username
    public void searchByUsername(String username) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        userField.clear();
        userField.sendKeys(username);
        driver.findElement(searchButton).click();
        waitForResults();
        driver.findElement(resetButton).click();
    }

    // 3. Search by user role (e.g., Admin, ESS)
    public void searchByUserRole(String role) {
        wait.until(ExpectedConditions.elementToBeClickable(roleDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(roleOption(role))).click();
        driver.findElement(searchButton).click();
        waitForResults();
        driver.findElement(resetButton).click();
    }

    // 4. Search by user status (Enabled / Disabled)
    public void searchByUserStatus(String status) {
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(statusOption(status))).click();
        driver.findElement(searchButton).click();
        waitForResults();
        driver.findElement(resetButton).click();
    }

    // Wait until results table appears
    private void waitForResults() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsTable));
    }
}
