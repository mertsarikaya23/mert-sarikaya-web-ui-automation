package com.mertsarikaya.webuiautomation.pages;

import com.mertsarikaya.webuiautomation.logging.StepLog;
import com.mertsarikaya.webuiautomation.selector.SelectorLoader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CareersOpenRolesPage extends BasePage {

    public static final String URL = "https://insiderone.com/careers/#open-roles";

    public CareersOpenRolesPage(WebDriver driver, SelectorLoader selectors) {
        super(driver, selectors);
    }

    public void openCareersPage() {
        driver.get(URL);
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(20));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        String current = w.until(d -> {
            String currentUrl = d.getCurrentUrl();
            return currentUrl != null && currentUrl.startsWith("https://insiderone.com/careers/") ? currentUrl : null;
        });
        StepLog.urlChecked("https://insiderone.com/careers/", current);
    }

    public void acceptCookiesIfPresent() {
        List<WebElement> els = driver.findElements(by("cookieAcceptAll"));
        if (!els.isEmpty()) {
            try {
                WebElement btn = wait.withTimeout(Duration.ofSeconds(4))
                        .until(ExpectedConditions.elementToBeClickable(by("cookieAcceptAll")));
                btn.click();
            } catch (Exception ignored) {
            } finally {
                wait.withTimeout(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
            }
        }
    }

    public void scrollToOpenRoles() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(by("openRolesSection")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    public void clickSeeAllTeams() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(by("seeAllTeams")));
        wait.until(ExpectedConditions.elementToBeClickable(btn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        jsClick(btn);
        StepLog.clicked("seeAllTeams");
    }

    public void clickQaOpenPositions() {
        By linkBy = by("qaOpenPositionsLink");
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(20));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        WebElement el = w.until(ExpectedConditions.visibilityOfElementLocated(linkBy));
        w.until(d -> {
            String t = el.getText();
            if (t == null) return false;
            String text = t.trim();
            if (!text.endsWith("Open Positions")) return false;
            String numberPart = text.replace("Open Positions", "").trim();
            try {
                return Integer.parseInt(numberPart) > 0;
            } catch (NumberFormatException e) {
                return false;
            }
        });
        w.until(ExpectedConditions.elementToBeClickable(el));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", el);
        jsClick(el);
        StepLog.clicked("qaOpenPositionsLink");
    }

    private void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }
}
