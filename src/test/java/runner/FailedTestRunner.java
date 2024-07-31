package runner;


import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * This class is used to run the failed Cucumber tests.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "@target/rerun.txt",
        glue = {"stepDefinitions"},
        plugin = {
                "pretty",
                "html:target/Cucumber-Reports/cucumber-html-report.html",
                "json:target/Cucumber-Reports/cucumber.json",
                "tech.grasshopper.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
public class FailedTestRunner {
}

