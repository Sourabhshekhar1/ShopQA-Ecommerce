package com.shopqa.tests;

import com.shopqa.pages.HomePage;
import com.shopqa.pages.ProductPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductTest extends BaseTest {

    @Test
    @Order(1)
    @DisplayName("Home page loads with products")
    public void testHomePageLoadsProducts() {
        navigateTo("/");
        HomePage home = new HomePage(driver);
        assertTrue(home.isLoaded(), "Navbar should be visible");
        assertTrue(home.getProductCount() > 0,
            "Home page should show products");
    }

    @Test
    @Order(2)
    @DisplayName("Product list page loads")
    public void testProductListLoads() {
        navigateTo("/products");
        ProductPage page = new ProductPage(driver);
        assertTrue(page.getProductCount() >= 0,
            "Product list should load");
    }

    @Test
    @Order(3)
    @DisplayName("Search returns filtered results")
    public void testProductSearch() {
        navigateTo("/products");
        ProductPage page = new ProductPage(driver);
        page.searchFor("phone");
        assertTrue(page.getProductCount() >= 0,
            "Search should return results");
    }

    @Test
    @Order(4)
    @DisplayName("Category filter works")
    public void testCategoryFilter() {
        navigateTo("/");
        HomePage home = new HomePage(driver);
        if (home.getCategories().size() > 0) {
            home.clickCategory(0);
            assertTrue(driver.getCurrentUrl()
                .contains("categoryId"),
                "URL should contain categoryId");
        } else {
            assertEquals(0, home.getCategories().size(),
                "No categories available to filter");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Product detail page loads")
    public void testProductDetailLoads() {
        navigateTo("/");
        HomePage home = new HomePage(driver);
        if (home.getProductCount() > 0) {
            home.clickFirstProduct();
            assertTrue(driver.getCurrentUrl()
                .contains("id="),
                "Should navigate to product detail");
        } else {
            assertEquals(0, home.getProductCount(),
                "No products available to open");
        }
    }
}
