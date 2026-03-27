package com.mertsarikaya.webuiautomation.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;
import java.util.Locale;

public final class DriverManager {

    public static final String BROWSER_PROPERTY = "browser";

    public static final String ALL_BROWSERS_PROPERTY = "e2e.allBrowsers";

    private static final Duration DEFAULT_IMPLICIT_WAIT = Duration.ofSeconds(10);

    private static final ThreadLocal<WebDriver> driverHolder = new ThreadLocal<>();

    private DriverManager() {}

    public static WebDriver getDriver() {
        if (driverHolder.get() == null) {
            initDriver();
        }
        return driverHolder.get();
    }

    public static void initDriver() {
        if (driverHolder.get() != null) {
            quitDriver();
        }
        String browser = System.getProperty(BROWSER_PROPERTY, "chrome").trim().toLowerCase(Locale.ROOT);
        WebDriver driver = createDriver(browser);
        driver.manage().timeouts().implicitlyWait(DEFAULT_IMPLICIT_WAIT);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        if (!Boolean.getBoolean("headless")) {
            driver.manage().window().maximize();
        }
        driverHolder.set(driver);
    }

    private static WebDriver createDriver(String browser) {
        switch (browser) {
            case "firefox":
            case "ff":
                return newFirefoxDriver();
            case "chrome":
                return newChromeDriver();
            default:
                throw new IllegalArgumentException(
                        "Desteklenmeyen tarayıcı: '" + browser + "'. Kullanın: chrome veya firefox.");
        }
    }

    private static WebDriver newChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications", "--start-maximized");
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver newFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("-headless");
        }
        return new FirefoxDriver(options);
    }

    public static void quitDriver() {
        WebDriver d = driverHolder.get();
        if (d != null) {
            d.quit();
            driverHolder.remove();
        }
    }
}
