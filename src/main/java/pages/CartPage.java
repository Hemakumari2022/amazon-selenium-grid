package pages;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.WaitUtils;

public class CartPage {
    private WebDriver driver;

 // Cart icon to open cart
    private By cartIcon = By.xpath("//span[contains(@class, 'bag') and contains(@class, 'bag--float-cart-closed')]");

    // Count inside cart icon
    private By cartCountInIcon = By.xpath("//span[contains(@class,'bag--float-cart-closed')]/span[@class='bag__quantity']");


    // All cart items container
    private By cartItemsContainer = By.cssSelector(".float-cart__shelf-container");

    // First item remove button (adjust class as needed)
    private By firstItemRemoveButton = By.cssSelector(".shelf-item .shelf-item__del");
    
    private By productInCartByName(String productName) {
        return By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div[1]' and contains(., '" + productName + "')]");
    }
    public static void waitForElementVisible(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openCart() {
        System.out.println("Opening cart.");
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        
        // Print debug info
        System.out.println("Cart icon locator: " + cartIcon);
        System.out.println("Page source length: " + driver.getPageSource().length());

        // Wait for cart icon to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartIcon));
        
        // Optional: Scroll into view if needed
        WebElement icon = driver.findElement(cartIcon);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", icon);

        icon.click();

        // Wait for cart items container to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartItemsContainer));
        System.out.println("Cart opened.");
    }


    public String getCartItemCount() {
        System.out.println("Getting cart item count from icon.");
        System.out.println(driver.getPageSource());
        String count = "0";
        try {
            WaitUtils.waitForElementVisible(driver, cartCountInIcon, 30);
            count = driver.findElement(cartCountInIcon).getText();
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Cart count element not found or not visible, assuming 0.");
        }
        System.out.println("Cart count: " + count);
        return count;
    }

    public void removeItemFromCart() {
        System.out.println("Attempting to remove first item from cart.");
        System.out.println(driver.getPageSource());
        try {
            WaitUtils.waitForElementVisible(driver, firstItemRemoveButton, 30);
            driver.findElement(firstItemRemoveButton).click();
            System.out.println("Remove button clicked.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("No item to remove or remove button not found.");
        }
    }
    public void removeItemFromCart2() {
        System.out.println("Attempting to remove second item from cart.");
        System.out.println(driver.getPageSource());
        try {
            WaitUtils.waitForElementVisible(driver, firstItemRemoveButton, 30);
            driver.findElement(firstItemRemoveButton).click();
            System.out.println("Remove button clicked.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("No item to remove or remove button not found.");
        }
    }
    
    public boolean isCartEmpty() {
        System.out.println("Checking if cart is empty.");
        try {
            WaitUtils.waitForElementVisible(driver, cartItemsContainer, 30);
            List<WebElement> items = driver.findElements(By.cssSelector("#cart_items .shelf-item"));
            return items.isEmpty();
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            return true;
        }
    }

    public boolean isProductInCart(String productName) {
        System.out.println("Checking if '" + productName + "' is in cart.");
        try {
            WaitUtils.waitForElementVisible(driver, cartItemsContainer, 30);
            return driver.findElement(productInCartByName(productName)).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}
