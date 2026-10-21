package authenticationTests;

import Config.LoadProperties;
import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.Authentication;

public class AuthenticationTest extends BaseTest {

    String invalidUsername = "Invalid1245";
    String incorrectPassword = "pass215";

    @DataProvider(name = "validUsers")
    public Object[][] validUsers() {
        String[] users = LoadProperties.getUsers();
        String sharedPassword = LoadProperties.getSharedPassword();
        Object[][] data = new Object[users.length][2];
        for (int i = 0; i < users.length; i++) {
            data[i][0] = users[i].trim();
            data[i][1] = sharedPassword;
        }
        return data;
    }

    @Test(dataProvider = "validUsers", priority = 1)
    public void validLoginTest(String user, String pass) {
        Authentication authentication = new Authentication(driver);

        authentication.validLogin(user, pass);

        Assert.assertTrue(
                authentication.isLoginSuccessful(),
                "User '" + user + "' should log in successfully and see the inventory page."
        );
        Assert.assertEquals(
                authentication.getAppLogoText(),
                "Swag Labs",
                "App logo should display 'Swag Labs' for user: " + user
        );
    }

    @Test(priority = 2)
    public void invalidLoginTest() {
        Authentication authentication = new Authentication(driver);

        authentication.invalidLoginCredentials(invalidUsername, incorrectPassword);

        String errorMessage = authentication.getErrorMessage();
        System.out.println("Error message displayed: " + errorMessage);

        Assert.assertEquals(errorMessage,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message should indicate invalid credentials."
        );
    }
}
