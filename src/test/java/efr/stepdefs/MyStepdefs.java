package efr.stepdefs;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sbtqa.tag.pagefactory.exceptions.PageInitializationException;
import ru.sbtqa.tag.stepdefs.ru.StepDefs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class MyStepdefs {

    private WebDriver driver;
    private String main_title;
    private String first_title;
    private String firstVacancyName;
    private String name;
    private SearchSettings searchSettings;
    private int pageNumber2;
    private int pageNumber;
    private WindowsTab windowsTab;
    private WebDriverWait wait;
    private static StepDefs stepDefs;
    private Actions actions;


    @Before
    public void setUp() {
        driver = DriverSettings.chromeDriver();
        driver
                .manage().
                timeouts().
                implicitlyWait(1, TimeUnit.SECONDS);
        driver.
                manage().
                timeouts().
                pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.
                manage().
                timeouts().
                setScriptTimeout(1, TimeUnit.SECONDS);
    }

    @After
    public void driverClose() {
        driver.close();
    }

    @Given("^открытие \"([^\"]*)\"$")
    public void openUrlFromJvmParameters(String URL) {
        windowsTab = new WindowsTab();
        windowsTab.
                setMainWindowHandle(driver);
        driver.get(URL);
    }

    @When("^пользователь проверяет, что находится на странице \"([^\"]*)\"$")
    public void elementTextTitleMain(String titleName) {

        checkPageCorrect(driver, titleName);
    }

    @Then("^пользователь \\(нажимает кнопку\\) в выподающем меню Организации с параметром Реестр зарегистрированных организаций$")
    public void pressButtonRegisterOfRegisteredOrganizations() {

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(4) > a"))).
                build().perform();
        driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(4) " +
                        "> ul > li > ul > li:nth-child(1) > a > span.link__text")).
                click();
    }

    @Then("^пользователь \\(нажимает кнопку\\) Уточнить параметры поиска$")
    public void pressButtonRefineYourSearch() {
        driver.
                findElement(By.cssSelector("#setParametersLink > a")).
                click();
    }

    @Then("^пользователь заполняет форму$")
    public void fillForm() {
        searchSettings = new SearchSettings(driver);
        name = "университет";
        searchSettings.
                fillInputField(driver,
                        By.cssSelector("#searchString"), name);

        WebElement FZ44 = driver.
                findElement(By.cssSelector("#fz94"));
        if (!FZ44.isSelected())
            FZ44.click();

        WebElement FZ223 = driver.
                findElement(By.cssSelector("#fz223"));
        if (FZ223.isSelected())
            FZ223.click();

        WebElement showBlockedOrganizations = driver.
                findElement(By.cssSelector("#withBlocked"));
        if (showBlockedOrganizations.isSelected())
            showBlockedOrganizations.click();

        WebElement organizationSeparateStructuralUnit = driver.
                findElement(By.cssSelector("#svrDivision"));
        if (!organizationSeparateStructuralUnit.isSelected())
            organizationSeparateStructuralUnit.click();

        driver.
                findElement(By.cssSelector("#organizationLevelTag > div > div.collapsed.height30 " +
                        "> span.msExpandButton")).
                click();
        ((JavascriptExecutor) driver).executeScript("document.getElementById('F').click()");
        ((JavascriptExecutor) driver).executeScript("document.getElementById('organizationLevelTagSelectBtn')" +
                ".getElementsByClassName('btnBtn blueBtn')[0].click()");

    }

    @Then("^пользователь \\(нажимает кнопку\\) с параметром Уточнить результаты$")
    public void pressButtonRefineResults() {
        driver.
                findElement(By.cssSelector("#searchButtonsBlock > div > span.bigOrangeBtn.margLeft20")).
                click();
    }

    @Then("^пользователь сохраняет в файл данные по каждому результату поиска Сокращенное наименование, Дата регистрации и Место нахождения$")
    public void rrr() {

        wait = new WebDriverWait(driver, 1);

        driver.findElement(By.cssSelector("li.pageSelect")).
                click();
        ((JavascriptExecutor) driver).
                executeScript("document.getElementById('_" + 20 + "').click()");
        try {
            Thread.sleep(2000);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        //Определяю количество страниц
        pageNumber2 = determineTheNumberOfPages();
        System.out.println(pageNumber2);
        FileOutputStream fos;
        try {
            int i = 1;
            while (i <= pageNumber2) {
                //При переходе на вторую страницу слева появляется элемент в виде стрелки и счет сдивигается на 1
                if (i == 3) i = 4;
                //Руки еще не дошли сделать нормальную проверку и ожидание
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (pageNumber2 != 1)
                    driver.findElement(By.cssSelector("body > div.parametrs.margBtm10 > div " +
                            "> div.paginator.greyBox.extendedVariant.margBtm20 " +
                            "> div.paginator.greyBox > ul > li:nth-child(" + i + ") > a > span")).click();
                i++;
                String nameOfPurchase;
                String applicationDeadline;
                String contactPerson;
                for (int j = 0; j < driver.findElements(By.cssSelector("dl.docInfo > dt > span")).size(); j++) {
                    fos = new FileOutputStream(System.getProperty("user.dir") + "/notes" + j + i + ".txt");
                    windowsTab.setOldWindowsSet(driver);
                    driver.
                            findElement(By.cssSelector("#exceedSphinxPageSizeDiv > div:nth-child(" + (j + 1) + ") " +
                                    "> div > ul > li:nth-child(1) > a")).
                            click();
                    driver.switchTo().window(windowsTab.getNewWindowHandle(driver));
                    try {
                        contactPerson = driver.findElement(By.cssSelector("#tab-info > div:nth-child(2) > div > table > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
                        nameOfPurchase = driver.
                                findElement(By.cssSelector("#tab-info > div:nth-child(2) > div > table > tbody > tr:nth-child(4) > td:nth-child(2)")).getText();
                        applicationDeadline = driver.
                                findElement(
                                        By.cssSelector("#tab-info > div:nth-child(2) > div > table > tbody > tr:nth-child(10) > td:nth-child(2)")).getText();
                    } catch (Exception e) {
                        try {
                            contactPerson = driver.findElement(By.cssSelector("body > div > div > div > div:nth-child(5) > div > table > tbody > tr:nth-child(5) > td:nth-child(2)")).getText();
                            nameOfPurchase = driver.
                                    findElement(By.cssSelector("body > div > div > div > div:nth-child(5) > div > table > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
                            applicationDeadline = driver.
                                    findElement(
                                            By.cssSelector("body > div > div > div > div:nth-child(5) > div > table > tbody > tr:nth-child(13) > td:nth-child(2)")).getText();
                        } catch (Exception ee) {
                            contactPerson = driver.findElement(By.cssSelector("#tab-info > div:nth-child(2) > div > table > tbody > tr:nth-child(2) > td:nth-child(2)")).getText();
                            nameOfPurchase = driver.
                                    findElement(By.cssSelector("#tab-info > div:nth-child(2) > div > table > tbody > tr:nth-child(3) > td:nth-child(2)")).getText();
                            applicationDeadline = driver.
                                    findElement(
                                            By.cssSelector("#tab-info > div:nth-child(2) > div > table > tbody > tr:nth-child(8) > td:nth-child(2)")).getText();

                        }
                    }
                    driver.close();
                    driver.
                            switchTo().
                            window(windowsTab.getMainWindowHandle());
                    byte[] buffer = (nameOfPurchase
                            + System.lineSeparator()
                            + applicationDeadline
                            + System.lineSeparator()
                            + contactPerson
                            + System.lineSeparator()
                            + System.lineSeparator()).
                            getBytes();
                    fos.write(buffer, 0, buffer.length);
                }
            }
        } catch (
                IOException ex) {

            System.out.println(ex.getMessage());
        }

    }

    //сравнивает титл страницы
    private static void checkPageCorrect(WebDriver driver, String title) {
        System.out.print("Тест " + title + ": ");
        if ((driver.getTitle()).
                equals(title))
            System.out.println("пройден.");
        else
            System.out.println("провален.");
    }

    //Определяю количество страниц
    private int determineTheNumberOfPages() {
        try {
            pageNumber = driver.findElements(By.cssSelector("body > div.parametrs.margBtm10 > div " +
                    "> div.paginator.greyBox.extendedVariant.margBtm20 > div.paginator.greyBox " +
                    "> ul > li")).
                    size();
            System.out.println(pageNumber);
            String numberOfPages = driver.findElement(By.cssSelector("body > div.parametrs.margBtm10 " +
                    "> div > div.paginator.greyBox.extendedVariant.margBtm20 > div.paginator.greyBox " +
                    "> ul > li:nth-child(" + pageNumber + ") > a")).
                    getAttribute("data-pagenumber");
            System.out.println(numberOfPages);
            return Integer.parseInt(numberOfPages);
        } catch (Exception e) {
            return 1;
        }
    }

}