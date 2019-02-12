package efr.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
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
    //    private String maxSum;
//    private String minSum;
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
                implicitlyWait(2, TimeUnit.SECONDS);
        driver.
                manage().
                timeouts().
                pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.
                manage().
                timeouts().
                setScriptTimeout(2, TimeUnit.SECONDS);
        main_title = "Госслужба";
        first_title = "Госслужба/Вакансии";
        firstVacancyName = "Госслужба/Вакансии/";
        name = "НИОКР";
//        maxSum = "300000000";
//        minSum = "100";


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
//        stepDefs = new StepDefs();

//        getStepDefs().goToUrl("https://gossluzhba.gov.ru/");
        driver.get(URL);
    }

    @When("^пользователь проверяет, что находится на странице \"([^\"]*)\"$")
    public void elementTextTitleMain(String titleName) throws PageInitializationException {
        if (titleName.equals("Вакансии")) titleName = first_title;
        if (titleName.equals("Первая вакансия")) titleName = firstVacancyName;

        checkPageCorrect(driver, titleName);
//        stepDefs.openPage(titleName);

    }

    @And("^пользователь проверяет, что элемент \"Ваканисии\" виден$")
    public void elementDisplayVacancy() throws NoSuchMethodException, PageInitializationException {
        try {
            waitElement(driver, By.cssSelector("body > div.container > div > div " +
                    "> nav > ul.nav.nav-pills.main-menu " +
                    "> li:nth-child(3) > a"), 10);
        } catch (Exception e) {
            System.out.println("Элемент не найден");
        }
//        stepDefs.userActionOneParam("нажимает кнопку", "Ваканисии");

    }

    @And("^пользователь \\(нажимает кнопку\\) с параметром \"Ваканисии\"$")
    public void pressButtonVacancy() {
        driver.
                findElement(By.cssSelector("body > div.container > div > div " +
                        "> nav > ul.nav.nav-pills.main-menu " +
                        "> li:nth-child(3) > a")).click();
    }

    //Task2
    @And("^пользователь переходит на страницу расширенного поиска по сайту$")
    public void searchForm() {
        driver.
                findElement(By.cssSelector("button.search__btn")).
                click();
        driver.
                findElement(By.cssSelector("a.extendedSearchLink.floatRight")).
                click();
    }

    //smokeTest.zakupki.gov.ru
    @And("^пользователь совершает, logIn$")
    public void loginForm() {
        actions = new Actions(driver);
        actions.moveToElement(driver.
                findElement(By.cssSelector("body > header.header.header-top " +
                        "> div > div > div:nth-child(4) > div > div " +
                        "> span.text.padLeft4"))).build().perform();
    }

    //smokeTest.zakupki.gov.ru
    @And("^пользователь совершает, logOut$")
    public void logoutForm() {

    }

    //smokeTest.zakupki.gov.ru
    @And("^пользователь проверяет, работоспособность List Menu$")
    public void listMenu() {
        actions = new Actions(driver);
        actions.moveToElement(driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(3) > a"))).
                build().perform();
        driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(3) " +
                        "> ul > li > ul > li:nth-child(4) > a > span.link__text")).
                click();
        checkPageCorrect(driver, "Дополнительная информация о закупках, контрактах");

        actions.moveToElement(driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(4) > a"))).
                build().perform();
        driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(4) > ul " +
                        "> li > ul > li:nth-child(2) > a > span.link__text")).
                click();
        checkPageCorrect(driver, "Результаты поиска");

        actions.moveToElement(driver.
                findElement(By.cssSelector("body > header.header.header-bottom " +
                        "> div > div > div > div > ul > ul > li:nth-child(8) > a"))).
                build().perform();
        driver.
                findElement(By.cssSelector("#menu-column-templateADDITIONAL_INFO0 " +
                        "> ul:nth-child(5) > div:nth-child(1) > li > a > span.link__text")).
                click();
        checkPageCorrect(driver, "Документы");
    }

    //Task2
    @And("^пользователь заполняет форму, минималтьная сумма (\\d+) и максимальная сумма (\\d+)$")
    public void fillFormMinAndMaxSum(String minSum, String maxSum) {
        searchSettings = new SearchSettings(driver);
        //Заполнение поля максимальная сумма
        searchSettings.
                fillInputField(driver,
                        By.cssSelector("#priceToGeneral"), maxSum);
        //Заполнение поля минимальная сумма
        searchSettings.
                fillInputField(driver,
                        By.cssSelector("#priceFromGeneral"), minSum);
        //Заполнение поля Закупки
        searchSettings.
                fillInputField(driver,
                        By.cssSelector("#searchString.autocompleteOrder" +
                                ".hint.clearValue.withoutAutocomplete"), name);
        //Выбор валюты
        driver.findElement(By.cssSelector("div.pseudoSelect.greyText")).
                click();
    }

    //Task2
    @And("^пользователь заполняет форму$")
    public void fillForm() {


        ((JavascriptExecutor) driver).
                executeScript("document.getElementById('1').click()");
        //Способ определения поставщика, подрядной организации
        try {
            driver.
                    findElement(By.xpath("//li[@id='placingWaysTag']/div/div/span")).
                    click();

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_ZKKD44').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_KESMBO').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_OKD504').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_ZKKU44').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_ZKK44').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_OK').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_OK44').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_OKD44').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_OKU44').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_OK504').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWay_SZ').click()");

            ((JavascriptExecutor) driver).
                    executeScript("document.getElementById('placingWaysSelectBtn').click()");
        } catch (Exception e) {
            System.out.println("Способ определения поставщика, подрядной организации: " +
                    "в списке отсутвуют искомые поля для выбора.");
        }
        //Этап закупки:
//        driver.findElement(By.xpath("//li[@id='orderStages']/div/div/span")).click();
//        ((JavascriptExecutor)driver).executeScript("document.getElementById('ca').click()");
//        ((JavascriptExecutor)driver).executeScript("document.getElementById('pc').click()");
//        ((JavascriptExecutor)driver).executeScript("document.getElementById('pa').click()");
//        ((JavascriptExecutor)driver).executeScript("document.getElementById('orderStagesSelectBtn')" +
//                ".getElementsByClassName('btnBtn blueBtn')[0].click()");
    }


    @And("^пользователь проверяет, что элемент \"Первая вакансия\" виден$")
    public void elementDisplayFirstVacancy() {
        try {
            waitElement(driver, By.cssSelector("body > div.container " +
                    "> div:nth-child(4) > div.col-xs-8 > a:nth-child(1) " +
                    "> div > p.title"), 10);
        } catch (Exception e) {
            System.out.println("Элемент не найден");
        }
    }

    @And("^пользователь \\(нажимает кнопку\\) с параметром \"Первая вакансия\"$")
    public void pressButtonFirstVacancy() {
        firstVacancyName = firstVacancyName + driver.findElement(
                By.cssSelector("body > div.container > div:nth-child(4) " +
                        "> div.col-xs-8 > a:nth-child(1) > div > p.title")).getText();
        driver.findElement(By.cssSelector("body > div.container " +
                "> div:nth-child(4) > div.col-xs-8 > a:nth-child(1) " +
                "> div > p.title")).click();
    }

    @And("^пользователь \\(нажимает кнопку\\) с параметром \"Найти\"$")
    public void pressButtonSearch() {
        driver.findElement(By.cssSelector("span.bigOrangeBtn.searchBtn")).
                click();
    }

    //Task2
    @And("^пользователь выставляет количество отображаемых элеиентов поиска на страние равное (\\d+)$")
    public void pressPageSelect(String a) {
        wait = new WebDriverWait(driver, 1);

        driver.findElement(By.cssSelector("li.pageSelect")).
                click();
        ((JavascriptExecutor) driver).
                executeScript("document.getElementById('_" + a + "').click()");
        try {
            Thread.sleep(2000);
        } catch (
                InterruptedException e) {
            e.printStackTrace();
        }

        //Определяю количество страниц
        pageNumber2 = determineTheNumberOfPages();
    }

    //Task2
    @And("^пользователь заходит собирает нужные данные и сохраняет в текстовый файл (\\d+)$")
    public void rrr(String fileName) {
        boolean flag = true;
        try (
                FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/notes" + fileName + ".txt")) {
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
                    driver.findElement(By.cssSelector("body > div.parametrs.margBtm10 " +
                            "> div.paginator.greyBox.extendedVariant.margBtm20 " +
                            "> div.paginator.greyBox > ul > li:nth-child(" + i + ")")).click();
                i++;
                WebElement sum;
                String nameOfPurchase;
                String applicationDeadline;
                String contactPerson;
                String electronicSignature;
                for (int j = 0; j < driver.findElements(By.cssSelector("dd > strong")).size(); j++) {
                    sum = driver.findElements(By.cssSelector("dd > strong")).get(j);
                    windowsTab.setOldWindowsSet(driver);
                    driver.
                            findElement(By.cssSelector("div:nth-child(" + (j + 3) + ") " +
                                    "> div.reportBox > ul > ul > li:nth-child(1) > a")).click();
                    driver.switchTo().window(windowsTab.getNewWindowHandle(driver));

                    try {
                        contactPerson = driver.findElement(By.cssSelector("body > div.cardWrapper > div " +
                                "> div > div.cardWrapper > div > div > div.contentTabBoxBlock " +
                                "> div.noticeTabBox.padBtm20 > div:nth-child(6) > table > tbody " +
                                "> tr:nth-child(2) > td:nth-child(2)")).getText();
                        nameOfPurchase = driver.
                                findElement(By.cssSelector("body > div.cardWrapper " +
                                        "> div > div > div.cardWrapper > div > div > div.contentTabBoxBlock " +
                                        "> div.noticeTabBox.padBtm20 > div:nth-child(2) > table > tbody > tr:nth-child(4) " +
                                        "> td:nth-child(2)\n")).getText();
                        applicationDeadline = driver.
                                findElement(
                                        By.cssSelector("body > div.cardWrapper > div " +
                                                "> div > div.cardWrapper > div > div > div.contentTabBoxBlock " +
                                                "> div.noticeTabBox.padBtm20 > div:nth-child(10) > table > tbody " +
                                                "> tr:nth-child(1) > td:nth-child(2)")).getText();
                        flag = true;
                    } catch (Exception e) {
                        contactPerson = driver.findElement(By.cssSelector("body > div > div > div " +
                                "> div.contentTabBoxBlock > div > div:nth-child(4) > table > tbody " +
                                "> tr:nth-child(4) > td:nth-child(2)")).getText();
                        nameOfPurchase = driver.
                                findElement(By.cssSelector("body > div > div > div > div.contentTabBoxBlock " +
                                        "> div > div:nth-child(2) > table > tbody > tr:nth-child(5) " +
                                        "> td:nth-child(2)")).getText();
                        applicationDeadline = driver.
                                findElement(
                                        By.cssSelector("body > div > div > div > div.contentTabBoxBlock > div " +
                                                "> div:nth-child(6) > table > tbody > tr:nth-child(2) " +
                                                "> td:nth-child(2)")).getText();
                        flag = false;
                    }
                    driver.close();
                    driver.
                            switchTo().
                            window(windowsTab.getMainWindowHandle());
                    if (flag) {
                        String URL = driver.
                                findElement(By.cssSelector("div:nth-child(" + (j + 3) + ") > div.boxIcons " +
                                        "> a.cryptoSignLink.linkPopUp.pWidth_840")).getAttribute("href");
                        windowsTab.setOldWindowsSet(driver);
                        ((JavascriptExecutor) driver).executeScript("window.open()");
                        driver.switchTo().window(windowsTab.getNewWindowHandle(driver));
                        driver.get(URL);
                    } else {
                        driver.
                                findElement(By.cssSelector("div:nth-child(" + (j + 3) + ") > div.boxIcons " +
                                        "> a.cryptoSignLink.linkPopUp.pWidth_840")).click();
                    }
                    driver.
                            findElement(By.cssSelector("div.addingTbl.expandTbl.elSignBlock > table " +
                                    "> tbody > tr.rowExpand > td:nth-child(1) > span")).click();
                    electronicSignature = driver.
                            findElement(By.cssSelector("tr.toggleTr.expandRow > td > div")).getText();
                    if (flag) {
                        driver.close();
                        driver.
                                switchTo().
                                window(windowsTab.getMainWindowHandle());
                    } else {
                        driver.findElement(By.cssSelector("#modal-edsContainer > div > div.modal-header " +
                                "> div > div.col-2.pl-0.text-right.margLeft19 > span > img")).click();
                    }
                    byte[] buffer = (nameOfPurchase
                            + System.lineSeparator()
                            + sum.getText()
                            + System.lineSeparator()
                            + applicationDeadline
                            + System.lineSeparator()
                            + contactPerson
                            + System.lineSeparator()
                            + electronicSignature
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

    //проверяет наличие элемента на странице
    private static Boolean waitElement(WebDriver driver, By by, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception e) {
            System.out.println("Элемент '" + by + "' не доступен.");
            return false;
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
            pageNumber = driver.findElements(By.cssSelector("body > div.parametrs.margBtm10 " +
                    "> div.paginator.greyBox.extendedVariant.margBtm20 " +
                    "> div.paginator.greyBox > ul.pages > li.page")).
                    size();
            String numberOfPages = driver.findElement(By.cssSelector("body > div.parametrs.margBtm10 " +
                    "> div.paginator.greyBox.extendedVariant.margBtm20 " +
                    "> div.paginator.greyBox > ul.pages > li:nth-child(" + pageNumber + ") > a")).
                    getAttribute("data-pagenumber");
            return Integer.parseInt(numberOfPages);
        } catch (Exception e) {
            return 1;
        }
    }

    public static StepDefs getStepDefs() {
        if (null == stepDefs) {
            stepDefs = new StepDefs();
        }

        return stepDefs;
    }
}