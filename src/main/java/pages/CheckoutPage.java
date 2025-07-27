package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;

    // Selector for the checkout button in the cart
    private By checkoutBtn = By.xpath("//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[3]/div[3]"); // May need update if DOM differs

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void proceedToCheckout() {
        try {
            if (driver.findElements(checkoutBtn).size() > 0) {
                driver.findElement(checkoutBtn).click();
            } else {
                System.out.println("Checkout button not available. Possibly cart is empty.");
            }
        } catch (Exception e) {
            System.out.println("Exception while trying to click Checkout: " + e.getMessage());
        }
    }
}
