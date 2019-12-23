package cucumberSteps;

import cucumber.api.java.en.When;
import pages.UserCollectionPage;

public class UserCollectionPageSteps {

    //Here we can choose the most popular game where is more votes in language dependence field
    @When("^(?:user )?click on the first game in User Collection$")
    public void clickOnFirstGameInUserCollection() {
        new UserCollectionPage().clickOnFirstGameInUserCollection();
    }
}
