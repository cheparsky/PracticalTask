package cucumberSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import pages.MainPage;

public class MainPageSteps {

    @Given("^that user is located on Main Page$")
    public void openMainPage() {
        new MainPage().openMainPage();
    }

    //Here we can add functionality where we choose another user if the first user hasn't owned games
    //In this case we should combine this step and next step
    @When("^(?:user )?click on the first user in Forums section$")
    public void clickOnFirstUserInForumSection() {
        new MainPage().clickOnFirstUserInForumsSection();
    }
}
