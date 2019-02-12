package efr.stepdefs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

class DriverSettings {

    static WebDriver ffoxDriver() {

        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver");
        return new FirefoxDriver();
    }

    static WebDriver chromeDriver() {
        System.setProperty(
                "webdriver.chrome.driver",
                System.getProperty("user.dir") + "/chromedriver"
        );
        return new org.openqa.selenium.chrome.ChromeDriver();
    }

}
