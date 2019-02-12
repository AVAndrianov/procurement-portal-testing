package efr;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        monochrome = true,
        glue = {"efr.stepdefs"},
        features = {"src/test/resources/features/"}
)

public class CucumberTest {
}