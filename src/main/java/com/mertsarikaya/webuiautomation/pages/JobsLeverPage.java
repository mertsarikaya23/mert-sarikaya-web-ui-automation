package com.mertsarikaya.webuiautomation.pages;

import com.mertsarikaya.webuiautomation.logging.StepLog;
import com.mertsarikaya.webuiautomation.selector.SelectorLoader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class JobsLeverPage extends BasePage {

    public JobsLeverPage(WebDriver driver, SelectorLoader selectors) {
        super(driver, selectors);
    }

    public void assertRedirectedToLeverQaPage() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        String current = w.until(d -> {
            String url = d.getCurrentUrl();
            if (url == null) {
                return null;
            }
            String low = url.toLowerCase(Locale.ROOT);
            return low.contains("jobs.lever.co/insiderone") && (low.contains("team=quality") || low.contains("quality%20assurance")) ? url : null;
        });
        StepLog.urlChecked("jobs.lever.co/insiderone + team=quality", current);
        find("leverQaText");
    }

    public void assertAllLeverQaJobPositionsAreQa() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(25));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        WebElement title = w.until(ExpectedConditions.visibilityOfElementLocated(by("leverSeniorSoftwareQaJobTitle")));
        String text = title.getText() == null ? "" : title.getText();
        String low = text.toLowerCase(Locale.ROOT);
        if (!low.contains("qa engineer")) {
            throw new AssertionError("Expected job title to contain 'QA Engineer', got: " + text);
        }
    }

    public void assertAllLeverQaJobDepartmentsAreQa() {
        String url = driver.getCurrentUrl().toLowerCase(Locale.ROOT);
        if (!(url.contains("team=quality") || url.contains("quality%20assurance"))) {
            throw new AssertionError("Expected QA team filter in URL, got: " + driver.getCurrentUrl());
        }
        find("leverQaText");
    }

    public void assertAllLeverQaJobLocationsAreIstanbul() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(25));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        WebElement span = w.until(ExpectedConditions.visibilityOfElementLocated(by("leverSeniorSoftwareQaJobLocationSpan")));
        String text = span.getText() == null ? "" : span.getText();
        String low = text.toLowerCase(Locale.ROOT);
        if (!low.contains("istanbul")) {
            throw new AssertionError("Expected location to contain 'Istanbul', got: " + text);
        }
    }

    public void clickFirstLeverApplyButton() {
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(25));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        w.until(ExpectedConditions.visibilityOfElementLocated(by("leverApplyButtons")));
        List<WebElement> buttons = driver.findElements(by("leverApplyButtons"));
        if (buttons.isEmpty()) {
            throw new AssertionError("No Lever Apply button found.");
        }
        WebElement first = buttons.get(0);
        w.until(ExpectedConditions.elementToBeClickable(first));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", first);
        jsClick(first);
        StepLog.clicked("leverApplyButtons:first");
    }

    public void assertOpenedSpecificLeverJobUrl() {
        String expected = "https://jobs.lever.co/insiderone/9975b5f6-2f14-4562-a58e-665648d9f7e6";
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        String current = w.until(d -> {
            String url = d.getCurrentUrl();
            return url != null && url.startsWith(expected) ? url : null;
        });
        StepLog.urlChecked(expected, current);
    }

    public void clickApplyThisJobButton() {
        WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(by("leverApplyThisJobButton")));
        wait.until(ExpectedConditions.elementToBeClickable(btn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", btn);
        jsClick(btn);
        StepLog.clicked("leverApplyThisJobButton");
    }

    public void assertLeverApplicationFormOpened() {
        String expected = "https://jobs.lever.co/insiderone/9975b5f6-2f14-4562-a58e-665648d9f7e6/apply";
        WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(30));
        w.pollingEvery(Duration.ofMillis(WAIT_POLL_MILLIS));
        String current = w.until(d -> {
            String url = d.getCurrentUrl();
            return url != null && url.startsWith(expected) ? url : null;
        });
        StepLog.urlChecked(expected, current);
        find("leverApplicationFormList");
    }

    private void jsClick(WebElement el) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
    }
}
