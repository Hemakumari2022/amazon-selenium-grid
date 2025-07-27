package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;

public class CheckoutTest extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
        loginPage.login("demouser", "testingisfun99");
    }

    @Test(priority = 1)
    public void testValidCheckoutFlow() {
        productPage.addFirstProductToCart();
        cartPage.openCart();
        checkoutPage.proceedToCheckout();

        // ✅ Assertion: Check for confirmation banner or redirect
       // boolean orderPlaced = driver.getPageSource().toLowerCase().contains("thank you")
         //       || driver.getCurrentUrl().toLowerCase().contains("order");

        //Assert.assertTrue(orderPlaced, "Order should be placed successfully with valid flow"); 
    }

    @Test(priority = 2)
    public void testCheckoutWithoutItems() {
        cartPage.openCart();
        checkoutPage.proceedToCheckout();

        // ❌ Expect no order placed or some error/validation
      //  boolean isStillOnCart = driver.getPageSource().toLowerCase().contains("add item")
//                || driver.getPageSource().toLowerCase().contains("cart is empty");

  //      Assert.assertTrue(isStillOnCart, "Checkout should not proceed without adding items");
    }
}
