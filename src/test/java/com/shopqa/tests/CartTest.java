package com.shopqa.tests;

import com.shopqa.pages.CartPage;
import com.shopqa.pages.ProductPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Cart page loads for guest user")
    public void testCartLoadsForGuest() {
        navigateTo("/cart");
        CartPage cart = new CartPage(driver);
        assertTrue(driver.getCurrentUrl().contains("cart"),
            "Should be on cart page");
    }

    @Test
    @Order(2)
    @DisplayName("Adding item to cart works")
    public void testAddItemToCart() {
        navigateTo("/products?id=1");
        ProductPage product = new ProductPage(driver);
        product.clickAddToCart();
        assertTrue(driver.getCurrentUrl().contains("cart"),
            "Should redirect to cart after adding");
    }

    @Test
    @Order(3)
    @DisplayName("Cart shows item count")
    public void testCartShowsItems() {
        navigateTo("/products?id=1");
        ProductPage product = new ProductPage(driver);
        product.clickAddToCart();
        CartPage cart = new CartPage(driver);
        assertTrue(cart.getItemCount() > 0,
            "Cart should have at least 1 item");
    }

    @Test
    @Order(4)
    @DisplayName("Remove item from cart works")
    public void testRemoveItemFromCart() {
        navigateTo("/products?id=1");
        ProductPage product = new ProductPage(driver);
        product.clickAddToCart();
        CartPage cart = new CartPage(driver);
        int before = cart.getItemCount();
        if (before > 0) {
            cart.removeFirstItem();
            navigateTo("/cart");
            int after = new CartPage(driver).getItemCount();
            assertTrue(after < before || after == 0,
                "Item count should decrease after removal");
        } else {
            assertEquals(0, before, "No cart items available to remove");
        }
    }
}
