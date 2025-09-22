
package com.taskmanager.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegisterPageTest extends BaseTest {

    @Test
    public void testRegisterPageElementsAndRegister() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate to Register page (assumes homepage or login page has register link)
        WebElement registerLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".link-btn")));
        registerLink.click();

        // Verify Register page heading is visible
        WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(),'Register')]")));
        Assert.assertTrue(heading.isDisplayed(), "Register page heading should be displayed");

        // Fill out registration form inputs
        String username = "user" + System.currentTimeMillis();
        String password = "Pass@123";

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);

        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".auth-btn")));
        registerButton.click();

        // Accept alert on successful registration
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Verify redirected to Login page by checking login form presence
        WebElement loginFormUsername = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        Assert.assertTrue(loginFormUsername.isDisplayed(), "Should be redirected to Login page");
    }
}
