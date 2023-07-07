package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.CommonMethods;

import java.io.IOException;

public class Hooks extends CommonMethods {
    @Before
    public void start(){
        openBrowserAndNavigateToURL();
    }

    @After
    public void end(Scenario scenario) throws IOException {
        // We need this variable bec the screenshot method returns array of byte
        byte[] pic;
        // Here we are going to capture the screenshot and attaching it to the report
        if (scenario.isFailed()) {
            pic = takeScreenshot("failed/"+scenario.getName());
        } else {
            pic = takeScreenshot("passed/"+scenario.getName());
        }

        // Attach this screenshot in the report
        scenario.attach(pic, "image/png", scenario.getName());

        closeBrowser();
    }
}
