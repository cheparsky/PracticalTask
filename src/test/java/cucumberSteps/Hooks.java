package cucumberSteps;

import cucumber.api.java.After;
import utilities.MyDriver;

public class Hooks {

    @After
    public void afterScenario() {
        MyDriver.close();
    }

}
