package cucumberSteps;

import cucumber.api.java.en.Then;
import pages.BoardGamePage;

public class BoardGameVerifyingSteps {

    @Then("^check if the value of most voted Language Dependence Level is as expected$")
    public void checkIfLanguageDependenceLevelIsAsExpected() {
        new BoardGamePage().checkIfLanguageDependenceLevelIsAsExpected();
    }

}
