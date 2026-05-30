package com.shopqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By fullNameField     = By.name("fullName");
    private By emailField        = By.name("email");
    private By passwordField     = By.name("password");
    private By confirmPassField  = By.name("confirmPassword");
    private By submitButton      = By.cssSelector("button[type='submit']");
    private By errorMessage      = By.cssSelector(".alert-error");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void register(String name, String email,
                         String password, String confirmPassword) {
        driver.findElement(fullNameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPassField).sendKeys(confirmPassword);
        driver.findElement(submitButton).click();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions
            .visibilityOfElementLocated(errorMessage)).getText();
    }
}
