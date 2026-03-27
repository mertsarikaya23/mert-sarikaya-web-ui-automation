package com.mertsarikaya.webuiautomation.logging;

public final class StepLog {

    private StepLog() {}

    public static void checked(String selectorName) {
        System.out.println("[ADIM] Kontrol edildi: " + selectorName);
    }

    public static void clicked(String selectorName) {
        System.out.println("[ADIM] Tıklandı: " + selectorName);
    }

    public static void urlChecked(String expected, String actual) {
        System.out.println("[ADIM] URL doğrulandı: beklenen=" + expected + " | mevcut=" + actual);
    }
}
