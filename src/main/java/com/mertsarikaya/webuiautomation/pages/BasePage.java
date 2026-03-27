package com.mertsarikaya.webuiautomation.pages;

import com.mertsarikaya.webuiautomation.logging.StepLog;
import com.mertsarikaya.webuiautomation.selector.SelectorLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected static final int WAIT_TIMEOUT_SECONDS = 10;
    protected static final int WAIT_POLL_MILLIS = 400;

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final SelectorLoader selectors;

    protected BasePage(WebDriver driver, SelectorLoader selectors) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        this.wait.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        this.selectors = selectors;
    }

    protected By by(String selectorName) {
        String type = selectors.getType(selectorName);
        String value = selectors.getLocator(selectorName);
        if (type == null) type = "css";
        switch (type.toLowerCase()) {
            case "id":
                return By.id(value);
            case "xpath":
                return By.xpath(value);
            case "css":
            case "cssselector":
                return By.cssSelector(value);
            case "name":
                return By.name(value);
            default:
                return By.cssSelector(value);
        }
    }

    protected WebElement find(String selectorName) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(by(selectorName)));
        StepLog.checked(selectorName);
        return el;
    }
}
