# mert_sarikaya_web_ui_automation

End-to-end **Selenium** automation against the [insiderOne](https://insiderone.com/) home page, careers section, and [Lever](https://jobs.lever.co/insiderone) job listings. The project follows the Page Object pattern. It centralizes selectors in JSON and captures screenshots on failed tests.

---

## Technologies

| Component | Version / notes |
|-----------|-----------------|
| **Java** | 11 |
| **Maven** | As defined in `pom.xml` (compiler 3.11, Surefire 3.2) |
| **Selenium** | 4.15.0 (`selenium-java`, `selenium-chrome-driver`, `selenium-firefox-driver`) |
| **JUnit 5** | 5.10.1 (parameterized tests: `junit-jupiter-params`) |
| **Gson** | 2.10.1 (selector JSON parsing) |
| **Browsers** | Chrome or Firefox (driver resolution via Selenium Manager) |

---

## Project layout

Summary of source and test resources (`target/` and IDE folders omitted on purpose):

```
mert_sarikaya_web_ui_automation/
├── pom.xml
├── README.md
├── .gitignore
└── src/
    ├── main/java/com/mertsarikaya/webuiautomation/
    │   ├── config/
    │   │   └── DriverManager.java
    │   ├── logging/
    │   │   └── StepLog.java
    │   ├── selector/
    │   │   └── SelectorLoader.java
    │   └── pages/
    │       ├── BasePage.java
    │       ├── InsiderOneHomePage.java
    │       ├── CareersOpenRolesPage.java
    │       └── JobsLeverPage.java
    └── test/
        ├── java/com/mertsarikaya/webuiautomation/tests/
        │   ├── BaseTest.java
        │   ├── InsiderOneCareersE2ETest.java
        │   ├── ScreenshotOnFailureExtension.java
        │   └── support/BrowserMatrix.java
        └── resources/selectors/
            ├── insiderone-home-page-selectors.json
            ├── insiderone-careers-selectors.json
            └── insiderone-job-lever-page-selectors.json
```

On test failure, screenshots are written under **`target/failure-screenshots/`**.

---

## Architecture notes

- **Page Object Model:** Each site area (`InsiderOneHomePage`, `CareersOpenRolesPage`, `JobsLeverPage`) encapsulates its own URL interactions and assertions; the test class is a thin orchestration layer.
- **Centralized selectors:** XPath / CSS strings live only in `src/test/resources/selectors/*.json`; Java accesses them via `by("key")`.
- **Headless:** When the system property `headless=true` is set, `DriverManager` runs Chrome (`--headless=new`) or Firefox (`-headless`) in headless mode.
- **Browser parameter:** If the JVM property **`browser`** alone is set (`-Dbrowser=chrome|firefox|ff`), a single run uses only that browser; `DriverManager` starts the matching session.
- **Dual browser (default):** If **`browser` is not set**, the test class reads **`e2e.allBrowsers`**: default **`true`** → the same scenario runs twice in order, **Chrome** then **Firefox**. If Firefox is not installed, use the table below to narrow the run.

---

## Browser selection

Which browser(s) run is produced from JVM properties in the `browsers()` method in **`src/test/java/com/mertsarikaya/webuiautomation/tests/support/BrowserMatrix.java`**; `InsiderOneCareersE2ETest` wires it with `@MethodSource("…BrowserMatrix#browsers")`.

The table summarizes the same rules:

| What you pass | Outcome |
|---------------|---------|
| No extra properties | **`e2e.allBrowsers` defaults to `true`** → **Chrome** first, then **Firefox** (both must be installed) |
| `-De2e.allBrowsers=false` and no `browser` | **Chrome** only (for machines without Firefox) |
| `-Dbrowser=chrome` or `-Dbrowser=firefox` (`ff` allowed) | Only that browser; `e2e.allBrowsers` is **ignored** |

**Examples:**

```bash
mvn clean test
mvn clean test -De2e.allBrowsers=false
mvn clean test -Dbrowser=chrome
mvn clean test -Dbrowser=firefox
```

---

## Requirements

- **JDK 11** (or compatible; compile target in `pom.xml` is 11)
- For **default `mvn clean test`**: **Google Chrome** and **Mozilla Firefox** must be installed
- Chrome only: `-De2e.allBrowsers=false` or `-Dbrowser=chrome`

---

## Running tests

From the project root:

```bash
cd mert_sarikaya_web_ui_automation
mvn clean test
```

Single test class:

```bash
mvn clean test -Dtest=InsiderOneCareersE2ETest
```
