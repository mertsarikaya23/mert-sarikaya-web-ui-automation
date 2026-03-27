package com.mertsarikaya.webuiautomation.tests;

import com.mertsarikaya.webuiautomation.config.DriverManager;
import com.mertsarikaya.webuiautomation.pages.CareersOpenRolesPage;
import com.mertsarikaya.webuiautomation.pages.InsiderOneHomePage;
import com.mertsarikaya.webuiautomation.pages.JobsLeverPage;
import com.mertsarikaya.webuiautomation.selector.SelectorLoader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@ExtendWith(ScreenshotOnFailureExtension.class)
class InsiderOneCareersE2ETest {

    @ParameterizedTest(name = "tarayici={0}")
    @MethodSource("com.mertsarikaya.webuiautomation.tests.support.BrowserMatrix#browsers")
    void e2eFromInsiderOneHomeThroughCareersToLeverQaJobApplication(String browser) {
        System.setProperty(DriverManager.BROWSER_PROPERTY, browser);
        DriverManager.initDriver();

        var driver = DriverManager.getDriver();
        SelectorLoader homeSelectors = new SelectorLoader("selectors/insiderone-home-page-selectors.json");
        SelectorLoader careersSelectors = new SelectorLoader("selectors/insiderone-careers-selectors.json");
        SelectorLoader leverSelectors = new SelectorLoader("selectors/insiderone-job-lever-page-selectors.json");

        InsiderOneHomePage home = new InsiderOneHomePage(driver, homeSelectors);
        home.open();
        home.assertInsiderOneHomeLoaded();
        home.assertHeaderElementsVisible();
        home.assertCtaElementsVisible();

        CareersOpenRolesPage careers = new CareersOpenRolesPage(driver, careersSelectors);
        careers.openCareersPage();
        careers.acceptCookiesIfPresent();
        careers.scrollToOpenRoles();
        careers.clickSeeAllTeams();
        careers.clickQaOpenPositions();

        JobsLeverPage lever = new JobsLeverPage(driver, leverSelectors);
        lever.assertRedirectedToLeverQaPage();
        lever.assertAllLeverQaJobPositionsAreQa();
        lever.assertAllLeverQaJobDepartmentsAreQa();
        lever.assertAllLeverQaJobLocationsAreIstanbul();
        lever.clickFirstLeverApplyButton();
        lever.assertOpenedSpecificLeverJobUrl();
        lever.clickApplyThisJobButton();
        lever.assertLeverApplicationFormOpened();
    }

    @AfterEach
    void tearDown() {
        DriverManager.quitDriver();
    }
}
