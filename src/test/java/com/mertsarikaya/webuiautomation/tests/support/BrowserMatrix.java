package com.mertsarikaya.webuiautomation.tests.support;

import com.mertsarikaya.webuiautomation.config.DriverManager;

import java.util.Locale;
import java.util.stream.Stream;

public final class BrowserMatrix {

    private BrowserMatrix() {}

    public static Stream<String> browsers() {
        String chosen = System.getProperty(DriverManager.BROWSER_PROPERTY);
        if (chosen != null && !chosen.isBlank()) {
            return Stream.of(chosen.trim().toLowerCase(Locale.ROOT));
        }
        if (Boolean.parseBoolean(System.getProperty(DriverManager.ALL_BROWSERS_PROPERTY, "true"))) {
            return Stream.of("chrome", "firefox");
        }
        return Stream.of("chrome");
    }
}
