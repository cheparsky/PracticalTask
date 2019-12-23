package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.MyDriver;

import java.util.HashMap;
import java.util.Map;

public class UserPage {

    public UserPage() {
        PageFactory.initElements(MyDriver.wd, this);
    }

    public Map<String, WebElement> fields = new HashMap<String, WebElement>();
    public static String url = "https://boardgamegeek.com/user";

    //ELEMENTS OF USER PAGE

    @FindBy(xpath = "//*[@id='maincontent']/div[1]/div/table/tbody/tr/td[2]/div/table[1]/tbody/tr[2]/td[2]/a[2]")
    public WebElement ownedBoardGames;

    public void clickOnOwnedBoardGames() {
        MyDriver.clickButtonCheckIfLinkChanged(ownedBoardGames,
                UserCollectionPage.url.length(),
                UserCollectionPage.url,
                "User Collection Page opened successfully",
                "User Collection Page didn't open",
                "There are no owned board game button in Board Games section on User Page");
    }


}
