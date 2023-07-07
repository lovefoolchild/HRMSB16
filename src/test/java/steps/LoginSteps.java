package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import utils.CommonMethods;
import utils.ConfigReader;
import utils.Log;


import java.time.Duration;

public class LoginSteps extends CommonMethods {

    // public WebDriver driver;

    @Given("user is navigated to HRMS application")
    public void user_is_navigated_to_hrms_application() {
        // To launch Chrome browser
        openBrowserAndNavigateToURL();
        //driver = new ChromeDriver();
        //driver.get("http://hrm.syntaxtechs.net/humanresources/symfony/web/index.php/auth/login");
        //driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @When("user enters valid admin username and password")
    public void user_enters_valid_admin_username_and_password() {
        // Creating the object of the class to access all the WebElements from it
        //LoginPage loginPage = new LoginPage();
        //WebElement usernameField = driver.findElement(By.id("txtUsername"));
        //WebElement passwordField = driver.findElement(By.id("txtPassword"));
        // Entering the credentials
        //usernameField.sendKeys(ConfigReader.getPropertyValue("username"));
        //passwordField.sendKeys(ConfigReader.getPropertyValue("password"));
        // We are calling DOMConfigurator which is asking for the file which we used to integrate logs in our project
        DOMConfigurator.configure("log4j.xml");
        Log.startTestCase("My Batch 16 test case starts here.");
        sendText(ConfigReader.getPropertyValue("username"), loginPage.usernameField);
        Log.info("My username has been entered.");
        sendText(ConfigReader.getPropertyValue("password"), loginPage.passwordField);
        Log.info("My password has been entered.");
    }

    @When("user clicks on login button")
    public void user_clicks_on_login_button() {
        //LoginPage loginPage = new LoginPage();
        //WebElement loginButton = driver.findElement(By.name("Submit"));
        //loginButton.click();
        click(loginPage.loginButton);
    }

    @Then("user is successfully logged in the application")
    public void user_is_successfully_logged_in_the_application() {
        System.out.println("My test case is passed");
    }

    @When("user enters valid ESS username and password")
    public void user_enters_valid_ess_username_and_password() {
        //LoginPage loginPage = new LoginPage();
        //WebElement usernameField = driver.findElement(By.id("txtUsername"));
        //WebElement passwordField = driver.findElement(By.id("txtPassword"));
        //entering the normal credentials
        //usernameField.sendKeys("dalima123");
        //passwordField.sendKeys("Hum@nhrm123");
        sendText("dalima123", loginPage.usernameField);
        sendText("Hum@nhrm123", loginPage.passwordField);
    }

    @When("user enters invalid admin username and password")
    public void user_enters_invalid_admin_username_and_password() {
        //LoginPage loginPage = new LoginPage();
        //WebElement usernameField = driver.findElement(By.id("txtUsername"));
        //WebElement passwordField = driver.findElement(By.id("txtPassword"));
        //entering the invalid credentials
        //usernameField.sendKeys("admin123");
        //passwordField.sendKeys("Hum@nhrm123");
        sendText("admin123", loginPage.usernameField);
        sendText("Hum#n", loginPage.passwordField);
    }

    @Then("error message is displayed")
    public void error_message_is_displayed() {
        System.out.println("Error message is displayed.");
    }

    @When("user enters {string} and {string} and verifying the {string} for the combinations")
    public void user_enters_and_and_verifying_the_for_the_combinations(String username, String password, String errorMessageExpected) {
        sendText(username, loginPage.usernameField);
        sendText(password, loginPage.passwordField);
        click(loginPage.loginButton);
        // Fetching the error message from the WebElement
        String errorMessageActual = loginPage.errorMessageField.getText();
        // Error message coming from feature file to which we can compare
        Assert.assertEquals("Values does not match", errorMessageExpected, errorMessageActual);

    }
}