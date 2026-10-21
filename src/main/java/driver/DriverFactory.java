
package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DriverFactory {

    public String browser;
    public static WebDriver driver;
    public JavascriptExecutor js;

    // Constructor with browser selection
    public DriverFactory(String browser) {
        this.browser = browser;
        setup();
    }

    public void setup() {

        if (browser.equalsIgnoreCase("firefox")) {

            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();

        } else if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        } else if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();

        } else {
            throw new IllegalArgumentException(
                "Unsupported browser: " + browser
                + ". Use chrome, firefox, or edge."
            );
        }

        js = (JavascriptExecutor) driver;

        driver.manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(10));

        driver.manage().deleteAllCookies();

        driver.manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver;
    }


     //Takes a screenshot and saves it under screenshots folder
    public static String takeScreenshot(String testName) {
        if (driver == null) return null;
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String path = System.getProperty("user.dir")
                    + File.separator + "screenshots"
                    + File.separator + testName + "_" + timestamp + ".png";
            FileUtils.copyFile(src, new File(path));
            return path;
        } catch (IOException e) {
            System.err.println("Screenshot failed for test: " + testName + " — " + e.getMessage());
            return null;
        }
    }
}
