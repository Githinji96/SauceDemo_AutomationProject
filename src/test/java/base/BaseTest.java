package base;

import Config.LoadProperties;
import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.ExtentReportManager;

public class BaseTest {

    protected WebDriver driver;

    @Parameters("env")
    @BeforeMethod
    public void setUp(@Optional("qa") String env) {
        // Allow Maven command-line override: mvn test -Denv=staging
        String resolvedEnv = System.getProperty("env", env);
        String url = LoadProperties.getUrlForEnv(resolvedEnv);

        DriverFactory factory = new DriverFactory("chrome");
        driver = factory.getDriver();
        driver.get(url);

        System.out.println("Running on environment: " + resolvedEnv.toUpperCase() + " → " + url);
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
