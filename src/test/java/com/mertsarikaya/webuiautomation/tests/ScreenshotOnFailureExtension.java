package com.mertsarikaya.webuiautomation.tests;

import com.mertsarikaya.webuiautomation.config.DriverManager;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotOnFailureExtension implements AfterTestExecutionCallback {

    private static final DateTimeFormatter TS = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isEmpty()) {
            return;
        }
        try {
            WebDriver driver = DriverManager.getDriver();
            if (!(driver instanceof TakesScreenshot)) {
                return;
            }
            byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Path dir = Paths.get("target", "failure-screenshots");
            Files.createDirectories(dir);
            String safe = context.getDisplayName().replaceAll("[^a-zA-Z0-9_.-]", "_");
            String name = safe + "_" + TS.format(LocalDateTime.now()) + ".png";
            Path file = dir.resolve(name);
            Files.write(file, png);
            System.err.println("[TEST] Failure screenshot: " + file.toAbsolutePath());
        } catch (Exception e) {
            System.err.println("[TEST] Could not save failure screenshot: " + e.getMessage());
        }
    }
}
