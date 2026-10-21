package addToCartTests;

import Config.LoadProperties;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.AddToCart;
import pages.Authentication;

public class AddToCartTests extends BaseTest {

    @BeforeMethod(dependsOnMethods = "setUp")
    public void login() {
        Authentication authentication = new Authentication(driver);
        authentication.validLogin(LoadProperties.getUsername(), LoadProperties.getPassword());

        Assert.assertTrue(
                authentication.isLoginSuccessful(),
                "Login should be successful before the test starts."
        );
    }

    @Test(priority = 1)
    public void addRandomProductToCart() {
        AddToCart addToCart = new AddToCart(driver);

        String selectedItem = addToCart.addRandomItemToCart();

        Assert.assertNotNull(selectedItem, "A random product should be added to the cart.");
        Assert.assertTrue(
                addToCart.getCartCount() > 0,
                "Cart badge should show at least 1 item."
        );
        System.out.println("Verified cart item: " + selectedItem);
    }
}
