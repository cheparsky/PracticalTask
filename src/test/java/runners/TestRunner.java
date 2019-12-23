package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features",
        glue = {"cucumberSteps"},
        plugin = {"pretty"}
        //also here we can add tags for running certain test based on tags
)

public class TestRunner {
}
