package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.MyDriver;

import java.util.HashMap;
import java.util.Map;

public class MainPage {

    public MainPage() {
        PageFactory.initElements(MyDriver.wd, this);
    }

    public Map<String, WebElement> fields = new HashMap<String, WebElement>();
    public static String url = "https://boardgamegeek.com";

    //ELEMENTS OF MAIN PAGE

    @FindBy(xpath = "//*[@id='results_14']/div/div/div[2]/div[2]/p/span[2]/a")
    public WebElement firstUserInForumsSection;

    public void clickOnFirstUserInForumsSection() {
        MyDriver.clickButtonCheckIfLinkChanged(firstUserInForumsSection,
                UserPage.url.length(),
                UserPage.url,
                "User Page opened successfully",
                "User Page didn't open",
                "There are no user button in Forums section on Main Page");
    }

    public void openMainPage() {
        MyDriver.runWDriver();
        MyDriver.wd.get(MainPage.url);
    }

}
