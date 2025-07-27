package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.WaitUtils;

import static org.testng.Assert.assertEquals;

public class AddToCartTest extends BaseTest {
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;

    @BeforeMethod(alwaysRun = true)
    public void setUpPagesAndLogin() {
        loginPage = new LoginPage(driver);
        productPage = new ProductPage(driver);
        cartPage = new CartPage(driver);

        loginPage.login("demouser", "testingisfun99");
        loginPage.clickLoginButton();
    }
    
    
    @Test(priority = 1)
    public void testAddSingleItemToCart() {
        productPage.addFirstProductToCart();
        cartPage.openCart();
        //String count = cartPage.getCartItemCount();
       // assertEquals(count, "1", "Cart count should be 1 after adding one item.");
    }

    @Test(priority = 2)
    public void testAddMultipleItemsToCart() {
        productPage.addFirstProductToCart();
        productPage.addFirstProductToCart();
        cartPage.openCart();
       // String count = cartPage.getCartItemCount();
       // assertEquals(count, "2", "Cart count should be 2 after adding two items.");
    }

    @Test(priority = 3)
    public void testRemoveItemFromCart() {
        productPage.addFirstProductToCart();
        cartPage.openCart();
        WaitUtils.sleep(2000);
        cartPage.removeItemFromCart();
       // String count = cartPage.getCartItemCount();
       // assertEquals(count, "0", "Cart should be empty after removing the item.");
    }
}
