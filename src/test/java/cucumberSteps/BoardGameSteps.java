package cucumberSteps;

import cucumber.api.java.en.Then;
import restRequests.BoardGamesRequests;

public class BoardGameSteps {

    @Then("^(?:we|user) get value of most voted Language Dependence Level using API$")
    public void clickOnFirstUserInForumSection() {
        new BoardGamesRequests().createAndSendRequest();
    }
}
