package com.shopqa.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected String baseUrl;
    protected Properties props;

    @BeforeEach
    public void setUp() throws Exception {
        props = new Properties();
        InputStream is = getClass().getClassLoader()
            .getResourceAsStream("test.properties");
        props.load(is);

        baseUrl = props.getProperty("base.url");
        int implicitWait = Integer.parseInt(
            props.getProperty("implicit.wait", "10"));

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");

        driver = new ChromeDriver(options);
        driver.manage().timeouts()
            .implicitlyWait(Duration.ofSeconds(implicitWait));
        wait = new WebDriverWait(driver, Duration.ofSeconds(implicitWait));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected void navigateTo(String path) {
        driver.get(baseUrl + path);
    }
}
