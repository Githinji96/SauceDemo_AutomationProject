package base;

import Config.LoadProperties;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import utils.ExtentReportManager;

public class BaseTest {

    protected WebDriver driver;
    protected String username = LoadProperties.getUsername();
    protected String password = LoadProperties.getPassword();

    @BeforeMethod
    public void setUp() {
        DriverFactory factory = new DriverFactory("chrome");
        driver = factory.getDriver();
        driver.get(LoadProperties.getQaUrl());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    @AfterSuite(alwaysRun = true)
    public void generateReport() {
        ExtentReportManager.convertHtmlToPdf();
    }
}
