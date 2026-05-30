package com.shopqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class AdminPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By statCards     = By.cssSelector(".stat-card");
    private By productTable  = By.cssSelector(".data-table tbody tr");
    private By addNameField  = By.name("name");
    private By addPriceField = By.name("price");
    private By addStockField = By.name("stockQty");
    private By addDescField  = By.name("description");
    private By addCatField   = By.name("categoryId");
    private By addSubmitBtn  = By.cssSelector(".add-product-form button[type='submit']");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getStatCardCount() {
        return driver.findElements(statCards).size();
    }

    public int getProductRowCount() {
        return driver.findElements(productTable).size();
    }

    public void addProduct(String name, String price,
                           String stock, String desc, String categoryIndex) {
        driver.findElement(addNameField).sendKeys(name);
        driver.findElement(addPriceField).sendKeys(price);
        driver.findElement(addStockField).sendKeys(stock);
        driver.findElement(addDescField).sendKeys(desc);
        new Select(driver.findElement(addCatField))
            .selectByIndex(Integer.parseInt(categoryIndex));
        driver.findElement(addSubmitBtn).click();
    }

    public void clickDeleteFirst() {
        List<WebElement> rows = driver.findElements(productTable);
        if (!rows.isEmpty()) {
            rows.get(0).findElement(
                By.cssSelector("button[value='deleteProduct']")).click();
        }
    }
}
