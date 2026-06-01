package com.shopqa.tests;

import com.shopqa.pages.LoginPage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    public void setUpPage() {
        navigateTo("/user?action=login");
        loginPage = new LoginPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Valid admin login redirects to home")
    public void testValidLogin() {
        String email    = props.getProperty("admin.email");
        String password = props.getProperty("admin.password");
        loginPage.login(email, password);
        assertTrue(driver.getCurrentUrl().endsWith("/")
            || driver.getCurrentUrl().contains("/shopqa"),
            "Should redirect after login");
    }

    @Test
    @Order(2)
    @DisplayName("Invalid login shows error message")
    public void testInvalidLogin() {
        loginPage.login("wrong@email.com", "wrongpassword");
        assertTrue(loginPage.isErrorDisplayed(),
            "Error message should be displayed");
    }

    @Test
    @Order(3)
    @DisplayName("Empty fields show error")
    public void testEmptyFields() {
        loginPage.login("", "");
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("login"),
            "Should stay on login page");
    }
}
