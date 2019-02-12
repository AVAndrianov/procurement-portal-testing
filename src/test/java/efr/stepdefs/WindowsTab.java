package efr.stepdefs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

class WindowsTab {
    private WebDriver driver;
    private String newWindowHandle;
    private String mainWindowHandle;
    private Set<String> oldWindowsSet;
    private Set<String> newWindowsSet;

    String getMainWindowHandle() {
        return mainWindowHandle;
    }

    void setMainWindowHandle(WebDriver driver) {
        this.driver = driver;
        mainWindowHandle = this.driver.getWindowHandle();
    }

    void setOldWindowsSet(WebDriver driver) {
        this.driver = driver;
        oldWindowsSet = this.driver.getWindowHandles();
    }

    String getNewWindowHandle(WebDriver driver) {
        this.driver = driver;
        newWindowsSet = this.driver.getWindowHandles();
        newWindowsSet.removeAll(oldWindowsSet);
        newWindowHandle = (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );
        return newWindowHandle;
    }
}
