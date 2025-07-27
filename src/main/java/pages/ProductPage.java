package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.WaitUtils;

public class ProductPage {
	private WebDriver driver;

	private By firstProductAddToCartBtn = By.xpath("//*[@id=\"1\"]/div[4]"); // Add to Cart for first product (Sauce Labs Backpack)
    private By secondProductAddToCartBtn = By.xpath("//*[@id=\"2\"]/div[4]");
    
     public ProductPage(WebDriver driver) {
        this.driver = driver;
    }
   
     public void addFirstProductToCart() {
         System.out.println("Adding first product to cart.");
         WaitUtils.waitForElementVisible(driver, firstProductAddToCartBtn, 10);
         driver.findElement(firstProductAddToCartBtn).click();
         System.out.println("First product added.");
     }

     public void addSecondProductToCart() {
         System.out.println("Adding second product to cart.");
         WaitUtils.waitForElementVisible(driver, secondProductAddToCartBtn, 10);
         driver.findElement(secondProductAddToCartBtn).click();
         System.out.println("Second product added.");
     }

}
