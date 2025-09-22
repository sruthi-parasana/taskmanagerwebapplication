
package com.taskmanager.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DashboardPageTest extends BaseTest {

    @Test
    public void testDashboardAfterLogin() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Assuming user already registered, so login first
        // You can register your user manually or reuse RegisterPageTest

        // Fill login form
        String username = "existingUser"; // Change to valid user
        String password = "validPassword";

        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.sendKeys(username);

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(password);

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".auth-btn")));
        loginButton.click();

        // Wait for dashboard to load (e.g., check for a known dashboard element)
        WebElement dashboardWelcome = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".dashboard-welcome")));
        
        Assert.assertTrue(dashboardWelcome.isDisplayed(), "Dashboard welcome message should be displayed");

        // Optionally verify logged in user name via JS or element
        String loggedUserName = (String) ((JavascriptExecutor) driver).executeScript("return localStorage.getItem('registeredUser');");
        Assert.assertNotNull(loggedUserName, "LocalStorage should have registeredUser item");
        Assert.assertTrue(loggedUserName.contains(username), "Logged in username should match");

    }
}
