package com.shopqa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cartRows      = By.cssSelector(".cart-table tbody tr");
    private By emptyCart     = By.cssSelector(".empty-cart");
    private By cartTotal     = By.cssSelector(".cart-total-row.total");
    private By checkoutBtn   = By.cssSelector("a.btn-primary");
    private By removeButtons = By.cssSelector("button[value='remove']");
    private By qtyInputs     = By.cssSelector(".cart-qty-input");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public int getItemCount() {
        return driver.findElements(cartRows).size();
    }

    public boolean isEmpty() {
        return !driver.findElements(emptyCart).isEmpty();
    }

    public String getCartTotal() {
        try {
            return driver.findElement(cartTotal).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void clickCheckout() {
        driver.findElement(checkoutBtn).click();
    }

    public void removeFirstItem() {
        List<WebElement> btns = driver.findElements(removeButtons);
        if (!btns.isEmpty()) btns.get(0).click();
    }

    public void updateFirstItemQty(String qty) {
        List<WebElement> inputs = driver.findElements(qtyInputs);
        if (!inputs.isEmpty()) {
            inputs.get(0).clear();
            inputs.get(0).sendKeys(qty);
            inputs.get(0).submit();
        }
    }
}
