package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Authentication {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By usernameField = By.xpath("//input[@class='input_error form_input']");
    private  By passwordField = By.id("password");
    private  By loginButton   = By.id("login-button");
    private  By appLogo = By.className("app_logo");

    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    // Constructor — driver is passed in from BaseTest
    public Authentication(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Valid login with correct credentials
    public void validLogin(String username, String password) {
        WebElement usernameInput = driver.findElement(usernameField);
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(passwordField);
        passwordInput.sendKeys(password);

        WebElement loginBtn = wait.until(
                ExpectedConditions.elementToBeClickable(loginButton));
        loginBtn.click();
    }



    // Returns true if login was successful (landed on inventory page)
    public boolean isLoginSuccessful() {
        return driver.getCurrentUrl().contains("inventory");
    }

    // Returns the app logo text
    public String getAppLogoText() {
        return driver.findElement(appLogo).getText();
    }

    // Attempt login with invalid credentials
    public void invalidLoginCredentials(String incorrectUsername, String invalidPassword) {
        driver.findElement(usernameField).sendKeys(incorrectUsername);
        driver.findElement(passwordField).sendKeys(invalidPassword);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    // Returns the error message text shown on failed login
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();

    }
}
