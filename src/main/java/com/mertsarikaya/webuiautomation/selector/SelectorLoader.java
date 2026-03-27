package com.mertsarikaya.webuiautomation.selector;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public final class SelectorLoader {

    private static final Gson GSON = new Gson();
    private final Map<String, SelectorDefinition> selectors;

    public SelectorLoader(String resourcePath) {
        this.selectors = loadFromResource(resourcePath);
    }

    private Map<String, SelectorDefinition> loadFromResource(String resourcePath) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalArgumentException("Selector resource not found: " + resourcePath);
            }
            return GSON.fromJson(
                    new InputStreamReader(is, StandardCharsets.UTF_8),
                    new TypeToken<Map<String, SelectorDefinition>>() {}.getType()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load selectors from " + resourcePath, e);
        }
    }

    private SelectorDefinition require(String selectorName) {
        SelectorDefinition def = selectors.get(selectorName);
        if (def == null) {
            throw new IllegalArgumentException("Unknown selector: " + selectorName);
        }
        return def;
    }

    public String getLocator(String selectorName) {
        return require(selectorName).getValue();
    }

    public String getType(String selectorName) {
        return require(selectorName).getType();
    }

    public static class SelectorDefinition {
        private String type;
        private String value;

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}
