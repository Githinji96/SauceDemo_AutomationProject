
package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

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
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=1920");
                firefoxOptions.addArguments("--height=1080");
            }
            driver = new FirefoxDriver(firefoxOptions);

        } else if (browser.equalsIgnoreCase("chrome")) {

            WebDriverManager.chromedriver().setup();
            // configure chrome before setup
            ChromeOptions options = new ChromeOptions();
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                options.addArguments("--headless=new");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--disable-gpu");
                options.addArguments("--window-size=1920,1080");
            }
            driver = new ChromeDriver(options);

        } else if (browser.equalsIgnoreCase("edge")) {

            WebDriverManager.edgedriver().setup();
            EdgeOptions edgeOptions = new EdgeOptions();
            if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
                edgeOptions.addArguments("--headless=new");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--window-size=1920,1080");
            }
            driver = new EdgeDriver(edgeOptions);

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
        if (driver == null)
            return null;
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
