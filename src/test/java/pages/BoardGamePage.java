package pages;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import restRequests.BoardGamesRequests;
import utilities.MyDriver;

import java.util.HashMap;
import java.util.Map;

public class BoardGamePage {

    public BoardGamePage() {
        PageFactory.initElements(MyDriver.wd, this);
    }

    public Map<String, WebElement> fields = new HashMap<String, WebElement>();
    public static String url = "https://boardgamegeek.com/boardgame";
    public static String currentUrl;

    //ELEMENTS OF USER PAGE

    @FindBy(xpath = "//overview-module/description-module/div/div[2]/div/div[3]/div[2]/div[1]/div/ul/li/div[2]/span/span")
    public WebElement mostVotedLanguageDependenceLevel;

    //also we can check not only a winner but also how much vote persent has winner

    public String getMostVotedLanguageDependenceLevel() {
        String mostVotedLanguageDependenceLevel = new BoardGamePage().mostVotedLanguageDependenceLevel.getText();
        return mostVotedLanguageDependenceLevel;
    }

    public void checkIfLanguageDependenceLevelIsAsExpected() {
        System.out.println(getMostVotedLanguageDependenceLevel());
        System.out.println(BoardGamesRequests.languageDependence);
        Assert.assertTrue(getMostVotedLanguageDependenceLevel().equals(BoardGamesRequests.languageDependence));
    }
}
