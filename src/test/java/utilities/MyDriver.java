package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MyDriver {

    //add all the necessary variables
    public static WebDriver wd;
    public static List<String> errList;
    public static Date currentDate;
    public static String date;
    public static int timeToWait = 15;

    private static int EXPLICITE_WAIT_TIMEOUT = 15;
    private static boolean isHeadless = Boolean.parseBoolean(System.getProperty("isHeadless", "false"));

    // We define the function that is responsible for connecting the appropriate driver for our tests
    public static WebDriver runWDriver() {

        WebDriverManager.chromedriver().setup(); // if we use webdriver manager
        //System.setProperty("webdriver.chromedriver.driver", "src/test/resources/chromedriver.exe"); //if we use binary file
        if (isHeadless) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors"); //here we initiate headless options and below add to our driver
            wd = new ChromeDriver(options);
        } else {
            wd = new ChromeDriver();
        }
        wd.manage().window().maximize();

        return wd;
    }

    public static WebDriver getWebDriver() {
        return wd;
    }

    public static void close() {
        MyDriver.getWebDriver().quit();
        ;
    }

    public static void switchToMainFrame() {
        getWebDriver().switchTo().defaultContent();
        getWebDriver().switchTo().frame("main");

    }

    // Definiujemy funkcje, ktora odpowiada za ustawienie czasu oczekiwania na element
    public static void setWebDriverWait(int timeSec) {
        timeToWait = timeSec;
    }

    // Definiujemy funkcje, ktora sprawdza czy element jest widoczny na stronie czy nie
    public static boolean isElementVisible(WebElement e) {
        int attempts = 0;
        while (attempts < 2) {
            try {
                new WebDriverWait(getWebDriver(), 20)
                        .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
                                .equals("complete"));
                new WebDriverWait(getWebDriver(), timeToWait).until(ExpectedConditions.visibilityOf(e));
                boolean result = false;
                result = e.isDisplayed();
                System.err.println("czy element " + e + " widoczny: " + result);
                return result;
            } catch (TimeoutException exx) {
                //Utils.takeScreenshot();
                throw new RuntimeException("Element is not visible. RuntimeException occured on " + e);
            } catch (StaleElementReferenceException es) {
                attempts++;
            }
        }
        if (attempts > 2) {
            //Utils.takeScreenshot();
            throw new RuntimeException("Element is not visible. StaleElementReferenceException occured on " + e);
        }
        return false;
    }

    public static boolean isElementExist(String xpath) {
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        boolean exists = !wd.findElements(By.xpath(xpath)).isEmpty();
        wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        return exists;
    }

    // Definiujemy funkcje, ktora sporawdza czy przycisk jest i czy jest klikalny
    public static void clickButton(WebElement element,  //webelement
                                   String successMsg, //Widomość w razie klikalności przyciska
                                   String errMsg1,   //Wiadomość w razie nieklikalności przycisku
                                   String errMsg2) {     //Wiadomośc w razie braku przycisku

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                System.err.println(successMsg);
            } catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sporawdza czy przycisk jest, czy jest klikalny i czy przekierowuje na odpowiednia strone
    public static void clickButtonCheckIfLinkChanged(WebElement element, //Webelement
                                                     int urli, //ilośc znaków z obecnej strony, która musi odpowiadać do pageTo
                                                     String pageTo,  //Strona do której musi przejść po wciskaniu okładki
                                                     String successMsg, //Widomość w razie przechodzenia do poprawnej strony
                                                     String errMsg1, //Wiadomość w razie nie przechodzenia do poprawnej strony, czyli przycisk nie klikalny
                                                     String errMsg2) { //Wiadomość w razie braku przycisku na stronie

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                if (getWebDriver().getCurrentUrl().substring(0, urli).equals(pageTo)) {
                    System.err.println(successMsg + " Jesteś na stronie: " + getWebDriver().getCurrentUrl());
                } else {
                    throw new RuntimeException(errMsg1 + " Nadal jesteś na stronie: " + getWebDriver().getCurrentUrl());
                }
            }
            //catch(WebDriverException wex){ WriteCookiesToFile.ReadCookies(getWebDriver()); }
            catch (Exception e) {
                throw new RuntimeException(e + errMsg1 + " Nadal jesteś na stronie: " + getWebDriver().getCurrentUrl(), e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sporawdza czy przycisk jest, czy jest klikalny i czy po kliknieciu jest pokazany docelowy element na stronie
    public static void clickButtonCheckIfElementIsVisible(WebElement element, //Webelement
                                                          WebElement elementCheck,  //Webelement za pomocą którego sprawdzamy czy przycisk działa
                                                          String successMsg, //Widomość w razie jeżeli elementCheck jest na stronie
                                                          String errMsg1, //Wiadomość w razie jeżeli elementCheck nie ma na stronie, czyli przycisk nie klikalny
                                                          String errMsg2) { //Wiadomość w razie braku przycisku na stronie

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                if (MyDriver.isElementVisible(elementCheck)) {
                    System.err.println(successMsg);
                } else {
                    throw new RuntimeException(errMsg1 + " Nadal jesteś na stronie: " + getWebDriver().getCurrentUrl());
                }
            }
            //catch(WebDriverException wex){ WriteCookiesToFile.ReadCookies(getWebDriver()); }
            catch (Exception e) {
                throw new RuntimeException(errMsg1 + " Nadal jesteś na stronie: " + getWebDriver().getCurrentUrl(), e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sporawdza czy przycisk jest, czy jest klikalny i czy po kliknieciu zniknal docelowy element na stronie
    public static void clickButtonCheckIfElementIsNotVisible(WebElement element, //Webelement
                                                             WebElement elementCheck,  //Webelement za pomocą którego sprawdzamy czy przycisk działa
                                                             String successMsg, //Widomość w razie jeżeli elementCheck jest na stronie
                                                             String errMsg1, //Wiadomość w razie jeżeli elementCheck nie ma na stronie, czyli przycisk nie klikalny
                                                             String errMsg2) { //Wiadomość w razie braku przycisku na stronie

        if (MyDriver.isElementVisible(element)) {
            try {
                element.click();
                if (!MyDriver.isElementVisible(elementCheck)) {
                    System.err.println(successMsg);
                } else {
                    throw new RuntimeException(errMsg1);
                }
            }
            //catch(WebDriverException wex){ WriteCookiesToFile.ReadCookies(getWebDriver()); }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sprwdza czy jest input, czy mozna w input wpisac dane i wpisuje dane
    public static void inputData(WebElement element, //Webelement
                                 String data, // tekst wprowadzany w input i zmianną globalną
                                 String errMsg1,  // Wiadomość w razie nieedytowania inputru
                                 String errMsg2) {    // Wiadomość w razie braku inputu

        if (MyDriver.isElementVisible(element)) {
            try {
                element.clear();
                element.sendKeys(data);
                System.err.println("dane: " + data + ", zostaly wpisane");
            }
            //catch(WebDriverException wex){ WriteCookiesToFile.ReadCookies(getWebDriver()); }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sprwdza czy jest input, czy mozna w inpucie skasowac dane i kasuje dane
    public static void inputDataClear(WebElement element, //Webelement
                                      String errMsg1,  // Wiadomość w razie nieedytowania inputru
                                      String errMsg2) {    // Wiadomość w razie braku inputu

        if (MyDriver.isElementVisible(element)) {
            try {
                element.clear();
            }
            //catch(WebDriverException wex){ WriteCookiesToFile.ReadCookies(getWebDriver()); }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sprawdza czy podany tekst jest taki sam jak tekst na stronie w podanym Label
    public static void Label(WebElement element, //Webelement
                             String labelText, //Tekst etykiety
                             String errMsg1, //Wiadomość w razie niepoprawnego tekstu etykiety
                             String errMsg2) {  //Wiadomośc w razie braku etykiety

        if (MyDriver.isElementVisible(element)) {
            try {
                if (!element.getText().trim().equals(labelText)) {
                    throw new RuntimeException(errMsg1);
                }
            }
            //catch(WebDriverException wex){ WriteCookiesToFile.ReadCookies(getWebDriver()); }
            catch (Exception e) {
                throw new RuntimeException(errMsg1, e);
            }
        } else {
            throw new RuntimeException(errMsg2);
        }
    }

    // Definiujemy funkcje, ktora sprawdza czy podana i obecna strony są te same, jeżeli te same to OK
    public static boolean ifWWWAreEquals(String page) {
        int pageLength = page.length();
        try {
            if (getWebDriver().getCurrentUrl().substring(0, pageLength).equals(page)) {
                System.err.println("Obecna strona nie różni się od żądanej strony - OK. Jesteś na stronie: " + getWebDriver().getCurrentUrl());
                return true;
            } else {
                System.err.println("Obecna strona różni się od żądanej strony. Jesteś na stronie: " + getWebDriver().getCurrentUrl());
                return false;
            }
        } catch (Exception e) {
            System.err.println("Obecna strona '" + getWebDriver().getCurrentUrl() + "' różni się ot żądanej strony '" + page + "'.");
            return false;
        }
    }

    // Definiujemy funkcje, ktora sprawdza czy podana i obecna strony nie są te same, jeżeli nie te same to OK
    public static boolean ifWWWNoEquals(String page) {
        int pageLength = page.length();
        try {
            if (getWebDriver().getCurrentUrl().substring(0, pageLength).equals(page)) {
                System.err.println("Obecna strona nie różni się ot żądanej strony. Jesteś na stronie: " + getWebDriver().getCurrentUrl());
                return false;
            } else {
                System.err.println("Obecna strona różni się od żądanej strony - OK. Jesteś na stronie: " + getWebDriver().getCurrentUrl());
                return true;
            }
        } catch (Exception e) {
            System.err.println("Obecna strona '" + getWebDriver().getCurrentUrl() + "' różni się od żądanej strony '" + page + "' (OK), ale jest błąd przy sprawdzeniu URL.");
            return true;
        }
    }

//    public static void quiteDriverIfNecessary() {
////        if (isDriverInitialized.get()) {
////            getWebDriver().quit();
////            wd.remove();
////            isDriverInitialized.remove();
////            System.err.println("WebDriver Quit");
////        }
////    }

    public static void click(WebElement field) {
        int attemptNumber = 0;
        while (true) {
            try {
                new WebDriverWait(getWebDriver(), 20)
                        .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
                                .equals("complete"));
                new WebDriverWait(getWebDriver(), EXPLICITE_WAIT_TIMEOUT)
                        .until(ExpectedConditions.elementToBeClickable(field));
                //MyDriver.highLighterMethod(getWebDriver(), field);
//                field.click();
                JavascriptExecutor executor = (JavascriptExecutor) getWebDriver();
                executor.executeScript("arguments[0].click();", field);
                break;
            } catch (TimeoutException e) {
                throw new RuntimeException("Unable to click on element. RuntimeException occured on " + field);
            } catch (StaleElementReferenceException e) {
                attemptNumber++;
            }
            if (attemptNumber > 2)
                throw new RuntimeException("Unable to click on element. StaleElementReferenceException occured on " + field);
        }
    }

    //Creating a custom function
    public static void highLighterMethod(WebDriver driver, WebElement element) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
        //Thread.sleep(500);
    }

    public static void setValue(WebElement field, String value) {
        try {
            WebElement input = new WebDriverWait(getWebDriver(), EXPLICITE_WAIT_TIMEOUT)
                    .until(ExpectedConditions.visibilityOf(field));
            input.clear();
            input.sendKeys(value);
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to setValue for field: " + e);
        }
    }

    public static void isElementFromListPresentOnPage(Map<String, WebElement> map, String webElementName) {
        if (!map.containsKey(webElementName))
            throw new RuntimeException(String.format("Searched field isn`t in list of all fields")); //old text was: "Field %s is undefined"
        WebElement element = map.get(webElementName);
        if (!MyDriver.isElementVisible(element))
            throw new RuntimeException("There are no element " + webElementName + " on page");
    }


}
