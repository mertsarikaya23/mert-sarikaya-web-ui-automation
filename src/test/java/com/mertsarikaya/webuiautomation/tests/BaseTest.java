package com.mertsarikaya.webuiautomation.tests;

import com.mertsarikaya.webuiautomation.config.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {

    @BeforeEach
    void setUp() {
        DriverManager.initDriver();
    }

    @AfterEach
    void tearDown() {
        DriverManager.quitDriver();
    }
}
