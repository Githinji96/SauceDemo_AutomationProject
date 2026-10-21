package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

public class AddToCart {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By addToCartButtons = By.cssSelector("[data-test^='add-to-cart']");
    private final By itemNames        = By.cssSelector(".inventory_item_name");
    private final By cartBadge        = By.cssSelector(".shopping_cart_badge");

    public AddToCart(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Picks a random item from the inventory, adds it to cart,
     * and returns the item name.
     */
    public String addRandomItemToCart() {
        // Wait for items to be visible
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(addToCartButtons));

        List<WebElement> buttons = driver.findElements(addToCartButtons);
        List<WebElement> names   = driver.findElements(itemNames);

        int randomIndex = new Random().nextInt(buttons.size());

        String selectedItemName = names.get(randomIndex).getText();
        buttons.get(randomIndex).click();

        System.out.println("Added to cart: " + selectedItemName);
        return selectedItemName;
    }

    /**
     * Returns the number of items currently in the cart.
     */
    public int getCartCount() {
        List<WebElement> badge = driver.findElements(cartBadge);
        if (badge.isEmpty()) return 0;
        return Integer.parseInt(badge.get(0).getText().trim());
    }
}
