# SauceDemo Test Automation Framework

A Selenium WebDriver test automation framework built with Java, TestNG, and Maven to test the login functionality and cart operations of [SauceDemo](https://www.saucedemo.com/).

---

## Tech Stack

| Tool | Version | Purpose |
|---|---|---|
| Java | 17 | Core language |
| Selenium WebDriver | 4.51.0 | Browser automation |
| TestNG | 7.12.0 | Test framework |
| WebDriverManager | 5.8.0 | Automatic driver management |
| ExtentReports | 5.0.9 | HTML & PDF test reporting |
| iText html2pdf | 4.0.3 | PDF report generation |
| Maven | 3.x | Build and dependency management |

---

## Project Structure

```
src/
├── main/java/
│   ├── Config/
│   │   └── LoadProperties.java       # Reads config.properties
│   ├── driver/
│   │   └── DriverFactory.java        # WebDriver setup and screenshot utility
│   └── pages/
│       ├── Authentication.java       # Login page actions
│       └── AddToCart.java            # Inventory/cart page actions
├── main/resources/
│   └── config.properties             # Credentials and test users (gitignored)
└── test/java/
    ├── base/
    │   └── BaseTest.java             # setUp, tearDown, report generation
    ├── authenticationTests/
    │   └── AuthenticationTest.java   # Q1: Login test cases
    ├── addToCartTests/
    │   └── AddToCartTests.java       # Q2: Add to cart test case
    ├── Listeners/
    │   └── Listeners.java            # TestNG listener for ExtentReports
    ├── reRunFailedTests/
    │   └── rerunFailedTestCases.java # Retry analyser (max 4 retries)
    └── utils/
        └── ExtentReportManager.java  # Report initialisation and PDF conversion
```

---

## Setup

### Prerequisites
- Java 17+
- Maven 3.x
- Google Chrome installed

### 1. Clone the repository
```bash
git clone <repository-url>
cd SauceDemoTest_Automation
```

### 2. Create config.properties
The credentials file is gitignored and must be created manually.

Create `src/main/resources/config.properties` with the following content:

```properties
# Environment URLs
qa.url=https://www.saucedemo.com/
staging.url=https://www.stg-saucedemo.com/

# Credentials
login.username=standard_user
login.password=secret_sauce

# Multi-user credentials for data provider tests
login.users=standard_user,problem_user,visual_user
```

### 3. Install dependencies
```bash
mvn clean install -DskipTests
```

---

## Running Tests

### Run full suite on QA (default)
```bash
mvn test
```

### Run full suite on Staging
```bash
mvn test -Denv=staging
```

### Switch environment in testng.xml
Open `testng.xml` and change:
```xml
<parameter name="env" value="qa"/>
```
to:
```xml
<parameter name="env" value="staging"/>
```

---

## Test Cases

### Q1 — Login Functionality (`AuthenticationTest`)

| Test | Priority | Description |
|---|---|---|
| `validLoginTest` | 1 | Logs in with multiple valid users via `@DataProvider`. Asserts inventory page URL and Swag Labs logo. |
| `invalidLoginTest` | 2 | Attempts login with invalid credentials. Asserts the error message text. |

Valid users tested (from `config.properties`):
- `standard_user`
- `problem_user`
- `visual_user`

### Q2 — Add to Cart (`AddToCartTests`)

| Test | Priority | Description |
|---|---|---|
| `addRandomProductToCart` | 1 | Logs in, picks a random item from the inventory, adds it to cart. Asserts item name is not null and cart badge count is > 0. |

---

## Environment Parameterisation

The framework supports running the full test suite against either environment with a single parameter change — satisfying the requirement that locators and messages are consistent across both environments.

| Environment | URL |
|---|---|
| QA | https://www.saucedemo.com/ |
| Staging | https://www.stg-saucedemo.com/ |

The `env` parameter is resolved in `BaseTest.setUp()`, which maps `qa` → `qa.url` and `staging` → `staging.url` from `config.properties`.

---

## Reports

After each test run, reports are generated in the `reports/` folder:

- `reports/ExtentReport.html` — interactive HTML report (open in browser)
- `reports/ExtentReport.pdf` — PDF version generated after suite completion

Screenshots on failure are saved to `screenshots/`.

---

## Configuration

| File | Purpose |
|---|---|
| `testng.xml` | Suite definition, environment parameter, listener registration |
| `config.properties` | Credentials and user list (gitignored) |
| `pom.xml` | Dependencies and Maven Surefire plugin config |
