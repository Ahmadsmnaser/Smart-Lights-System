package com.steps;

import com.smartlights.entity.Light;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

public class LightSteps {
    private Light light;
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

    private WebDriver driver;
    @Given("The user opens the Devices page")
    public void The_user_opens_the_Devices_page(){
        driver.get("http://localhost:4200/devices");
        assertEquals("http://localhost:4200/devices" , driver.getCurrentUrl());
    }
    
    @Then("The user should be in the Devices page")
    public void The_user_should_be_in_the_Devices_page(){
        assertEquals("http://localhost:4200/devices" , driver.getCurrentUrl());
    }

    @Then("The user should see the lights list")
    public void The_user_should_see_the_lights_list(){
        assertTrue(driver.findElement(By.cssSelector(".light-card")).isDisplayed());
    }


    @Then("Light 1 exists with status ON")
    public void Light_exists_with_status_ON(){
        WebElement light1Card = driver.findElement(By.xpath("//h2[contains(text(),'Light 1')]/ancestor::div[contains(@class,'card') or contains(@class,'light')]"));
        assertTrue(light1Card.getText().contains("ON"));
    }
    @Then("Light 1 exists with status OFF")
    public void Light_exists_with_status_OFF(){
        WebElement light1Card = driver.findElement(By.xpath("//h2[contains(text(),'Light 1')]/ancestor::div[contains(@class,'card') or contains(@class,'light')]"));
        assertTrue(light1Card.getText().contains("OFF"));
    }



    @When("User toggles the light")
    public void User_toggles_the_light(){
        WebElement toggleButton = driver.findElement(By.xpath("(//h2[contains(text(),'Light 1')]/ancestor::div[contains(@class,'card') or contains(@class,'light')]//button[contains(text(),'Toggle')])[1]"));
        toggleButton.click();
    }

    
    @Then("The light state should become OFF")
    public void The_light_state_should_become_OFF(){
        WebElement light1Card = driver.findElement(By.xpath("//h2[contains(text(),'Light 1')]/ancestor::div[contains(@class,'card') or contains(@class,'light')]"));
        assertTrue(light1Card.getText().contains("OFF"));
    }

    @Then("The light state should become ON")
    public void The_light_state_should_become_ON(){
        WebElement light1Card = driver.findElement(By.xpath("//h2[contains(text(),'Light 1')]/ancestor::div[contains(@class,'card') or contains(@class,'light')]"));
        assertTrue(light1Card.getText().contains("ON"));
    }
}