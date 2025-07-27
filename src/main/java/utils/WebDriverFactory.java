package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {
    public static WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sankar\\.cache\\selenium\\chromedriver\\win64\\138.0.7204.157\\chromedriver.exe");
        return new ChromeDriver();
    }
}