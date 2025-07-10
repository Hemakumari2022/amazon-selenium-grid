package amazontest12;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
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
    public void testAmazon() {
        driver.get("https://www.amazon.in/");
        System.out.println("Title: " + driver.getTitle());
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
