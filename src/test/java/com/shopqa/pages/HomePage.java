package com.shopqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    private By productCards  = By.cssSelector(".product-card");
    private By categoryLinks = By.cssSelector(".grid-3 a");
    private By navbarBrand   = By.cssSelector(".navbar-brand");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public int getProductCount() {
        return driver.findElements(productCards).size();
    }

    public List<WebElement> getCategories() {
        return driver.findElements(categoryLinks);
    }

    public boolean isLoaded() {
        return driver.findElement(navbarBrand).isDisplayed();
    }

    public void clickFirstProduct() {
        List<WebElement> cards = driver.findElements(productCards);
        if (!cards.isEmpty()) {
            cards.get(0).findElement(
                By.cssSelector(".btn")).click();
        }
    }

    public void clickCategory(int index) {
        List<WebElement> cats = getCategories();
        if (index < cats.size()) {
            cats.get(index).click();
        }
    }
}
