package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.MyDriver;

import java.util.HashMap;
import java.util.Map;

public class UserCollectionPage {

    public UserCollectionPage() {
        PageFactory.initElements(MyDriver.wd, this);
    }

    public Map<String, WebElement> fields = new HashMap<String, WebElement>();
    public static String url = "https://boardgamegeek.com/collection/user";

    //ELEMENTS OF USER PAGE

    @FindBy(xpath = "//*[@id='results_objectname1']/a")
    public WebElement firstGameInUserCollection;

    public void clickOnFirstGameInUserCollection() {
        MyDriver.clickButtonCheckIfLinkChanged(firstGameInUserCollection,
                BoardGamePage.url.length(),
                BoardGamePage.url,
                "Board Game Page opened successfully",
                "Board Game Page didn't open",
                "There are no user button in Board Games section on User Page");
        BoardGamePage.currentUrl = MyDriver.wd.getCurrentUrl();
    }
}
