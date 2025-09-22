package com.taskmanager.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends BaseTest {

    @Test
    public void testLoginAfterRegistration() {
        // Generate unique username for registration
        String username = "user" + System.currentTimeMillis();
        String password = "Pass@123";

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step 1: Go to Register page by clicking register link
        WebElement registerLink = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".link-btn")));
        registerLink.click();

        // Step 2: Fill registration form
        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys(username);
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys(password);

        // Step 3: Click Register button
        WebElement registerButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".auth-btn")));
        registerButton.click();

        // Step 4: Accept alert after successful registration
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();

        // Step 5: Now on Login page, fill login form with registered credentials
        usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameField.sendKeys(username);
        passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        passwordField.sendKeys(password);

        // Step 6: Click Login button
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".auth-btn")));
        loginButton.click();

        // Step 7: Verify login success in frontend by checking localStorage key
        Boolean loggedIn = (Boolean) ((JavascriptExecutor) driver).executeScript(
                "return localStorage.getItem('registeredUser') !== null;"
        );

        Assert.assertTrue(loggedIn, "User should be logged in and localStorage key 'registeredUser' should be set.");

        // Optional: Pause for visual confirmation (remove in actual automated runs)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
