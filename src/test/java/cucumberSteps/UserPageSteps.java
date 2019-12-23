package cucumberSteps;

import cucumber.api.java.en.When;
import pages.UserPage;

public class UserPageSteps {

    @When("^(?:user )?click on owned Board Games$")
    public void clickOnOwnedBoardGames() {
        new UserPage().clickOnOwnedBoardGames();
    }
}
