package efr.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchSettings {
    private WebDriver driver;

    public SearchSettings(WebDriver driver) {
        this.driver = driver;
    }

    public void fillInputField(WebDriver driver, By by, String text) {
        WebElement webElement = driver.findElement(by);
        for (int i = 0; i < text.length(); i++) {
            webElement.sendKeys(String.valueOf(text.charAt(i)));
        }
    }
}
