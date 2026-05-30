package com.shopqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By searchInput   = By.cssSelector(".search-bar input");
    private By searchButton  = By.cssSelector(".search-bar button");
    private By productCards  = By.cssSelector(".product-card");
    private By addToCartBtn  = By.cssSelector("button[name='action']");
    private By productTitle  = By.cssSelector(".product-detail-title");
    private By productPrice  = By.cssSelector(".product-detail-price");
    private By qtyInput      = By.cssSelector(".qty-input");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void searchFor(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        driver.findElement(searchButton).click();
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public String getProductTitle() {
        return driver.findElement(productTitle).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public void setQuantity(String qty) {
        WebElement input = driver.findElement(qtyInput);
        input.clear();
        input.sendKeys(qty);
    }

    public void clickAddToCart() {
        driver.findElement(addToCartBtn).click();
    }
}
