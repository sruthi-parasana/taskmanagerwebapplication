
package com.taskmanager.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.Reporter;

public class BaseTest {

    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        // Set path to your chromedriver if needed, else ensure it's in system PATH
        // System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        Reporter.log("Starting browser session", true);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        // Change URL to your application's home or login page
        driver.get("http://localhost:8080/taskmanager");
        Reporter.log("Navigated to the application", true);
    }

    @AfterClass
    public void tearDown() {
        Reporter.log("Closing browser session", true);
        if (driver != null) {
            driver.quit();
        }
    }
}
