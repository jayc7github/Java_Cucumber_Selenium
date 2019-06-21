/**
 * 
 */
package runners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * @author Jayesh
 *
 */




@RunWith(Cucumber.class)
@CucumberOptions(
features = "src/test/resources/featureTests",
glue= {"stepDefinitions"}
)

public class TestRunner {

}



