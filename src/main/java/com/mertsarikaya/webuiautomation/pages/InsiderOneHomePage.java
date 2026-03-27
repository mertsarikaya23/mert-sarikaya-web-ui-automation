package com.mertsarikaya.webuiautomation.pages;

import com.mertsarikaya.webuiautomation.selector.SelectorLoader;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

public class InsiderOneHomePage extends BasePage {

    public static final String URL = "https://insiderone.com/";

    public InsiderOneHomePage(WebDriver driver, SelectorLoader selectors) {
        super(driver, selectors);
    }

    public void open() {
        driver.get(URL);
    }

    public void assertInsiderOneHomeLoaded() {
        String t = driver.getTitle().toLowerCase(Locale.ROOT);
        if (!t.contains("insider one") && !t.contains("insiderone")) {
            throw new AssertionError("Expected Insider One home page title, got: " + driver.getTitle());
        }
    }

    public void assertHeaderElementsVisible() {
        find("homeLogo");
        find("homeMenuPlatform");
        find("homeMenuIndustries");
        find("homeMenuCustomers");
        find("homeMenuResources");
    }

    public void assertCtaElementsVisible() {
        find("homePlatformTour");
        find("homeGetDemoButton");
    }
}
