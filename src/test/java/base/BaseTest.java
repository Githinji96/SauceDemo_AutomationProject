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
    protected String username = LoadProperties.getUsername();
    protected String password = LoadProperties.getPassword();

    @Parameters({"env", "browser"})
    @BeforeMethod
    public void setUp(@Optional("qa") String env,
                      @Optional("chrome") String browser) {
        // Allow Maven command-line overrides: mvn test -Denv=staging -Dbrowser=firefox
        String resolvedEnv     = System.getProperty("env", env);
        String resolvedBrowser = System.getProperty("browser", browser);
        String url             = LoadProperties.getUrlForEnv(resolvedEnv);

        DriverFactory factory = new DriverFactory(resolvedBrowser);
        driver = factory.getDriver();
        driver.get(url);

        System.out.println("Environment : " + resolvedEnv.toUpperCase() + " → " + url);
        System.out.println("Browser     : " + resolvedBrowser.toUpperCase());
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
