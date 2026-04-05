package com.steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PauseAndResumeSteps {
    private WebDriver driver;

    @Before
    public void setUp(){
        driver = new ChromeDriver();
    }

    @After
    public void tearDown(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("Light 1 is paused")
    public void Light_1_is_paused(){

    }
    @Given("Light 1 is not paused")
    public void Light_1_is_not_paused(){

    }
    @When("The user clicks Pause")
    public void the_user_clicks_Pause(){

    }
    @When("The user clicks Resume")
    public void the_user_clicks_Resume(){

    }
    @Then("Light 1 should not be paused")
    public void Light_1_should_not_be_paused(){

    }
    @Then("Light 1 should be paused")
    public void Light_1_should_be_paused(){

    }
}
