package amazontest12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.Capabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class AmazonTest1 {
    WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void setup(@Optional("chrome") String browser) throws MalformedURLException {
        @SuppressWarnings("deprecation")
		URL gridUrl = new URL("http://localhost:4445/wd/hub");

        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new RemoteWebDriver(gridUrl, new ChromeOptions());
                break;
            case "firefox":
                driver = new RemoteWebDriver(gridUrl, new FirefoxOptions());
                break;
            case "edge":
                driver = new RemoteWebDriver(gridUrl, new EdgeOptions());
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        driver.manage().window().maximize();
    }

    @Test
    public void testAmazon() throws InterruptedException {
        driver.get("https://www.amazon.in/");
        System.out.println("Page title: " + driver.getTitle());

        // Print browser name from Grid session
        Capabilities caps = ((RemoteWebDriver) driver).getCapabilities();
        System.out.println("Running on browser: " + caps.getBrowserName());

        // Keep session alive for 10 seconds to see it in Selenium Grid
        Thread.sleep(10000); // 10 seconds

        System.out.println("Test executed on: " + caps.getBrowserName());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
