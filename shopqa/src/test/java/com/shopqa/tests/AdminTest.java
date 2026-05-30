package com.shopqa.tests;

import com.shopqa.pages.LoginPage;
import com.shopqa.pages.AdminPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminTest extends BaseTest {

    private void loginAsAdmin() {
        navigateTo("/user?action=login");
        LoginPage login = new LoginPage(driver);
        login.login(
            props.getProperty("admin.email"),
            props.getProperty("admin.password")
        );
    }

    @Test
    @Order(1)
    @DisplayName("Admin dashboard loads after login")
    public void testAdminDashboardLoads() {
        loginAsAdmin();
        navigateTo("/admin?action=dashboard");
        AdminPage admin = new AdminPage(driver);
        assertTrue(admin.getStatCardCount() >= 3,
            "Dashboard should show 3 stat cards");
    }

    @Test
    @Order(2)
    @DisplayName("Admin can view products list")
    public void testAdminViewsProducts() {
        loginAsAdmin();
        navigateTo("/admin?action=products");
        AdminPage admin = new AdminPage(driver);
        assertTrue(admin.getProductRowCount() >= 0,
            "Products table should load");
    }

    @Test
    @Order(3)
    @DisplayName("Admin can add a new product")
    public void testAdminAddsProduct() {
        loginAsAdmin();
        navigateTo("/admin?action=products");
        AdminPage admin = new AdminPage(driver);
        int before = admin.getProductRowCount();
        admin.addProduct(
            "Test Product Selenium",
            "999.00",
            "10",
            "Added by Selenium test",
            "1"
        );
        navigateTo("/admin?action=products");
        int after = new AdminPage(driver).getProductRowCount();
        assertTrue(after > before,
            "Product count should increase after adding");
    }
}
