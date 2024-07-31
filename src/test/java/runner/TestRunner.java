package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * This class is used to run the Cucumber tests for the project.
 * It specifies the location of the feature files and step definitions,
 * and configures the plugins for generating reports and rerun files.
 * <p>
 * Author: Emray Pala
 * Created Date: 2024-07-30
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-html-report.html",
                "json:target/cucumber.json",
                "rerun:target/rerun.txt"} // Plugin for generating the rerun file
)
public class TestRunner {
}