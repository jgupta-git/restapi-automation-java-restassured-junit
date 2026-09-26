# REST API Test Automation

BDD API test framework using REST Assured, Cucumber, and JUnit 4.

## Tech Stack

Java 11 | REST Assured 5.5.1 | Cucumber 7.34.9 | JUnit 4.13.2 | Maven

## APIs Under Test

- **AutomationExercise** — Products, Brands, Auth, User Accounts
- **DummyJSON** — JWT Auth, Products (query params, sorting, pagination, search)

## Test Suites (39 Scenarios)

| Feature | Scenarios | Description |
|---------|-----------|-------------|
| `product_api` | 5 | GET product list, POST 405, search products, missing search param |
| `brand_api` | 2 | GET brand list, PUT 405 method validation |
| `auth_api` | 4 | Valid login, missing email, DELETE 405, invalid credentials |
| `user_account_api` | 5 | Full CRUD — create, read, update, delete, verify deletion |
| `token_auth_api` | 6 | JWT login, Bearer access, token refresh, invalid/missing token |
| `query_params` | 9 | Pagination (limit/skip), field select, category filter, search, Scenario Outline |
| `sorting` | 3 | Sort by price asc/desc, rating desc — verified with list comparison |
| `hamcrest_assertions` | 2 | Chained body assertions via DataTable, everyItem collection matchers |
| `schema_validation` | 3 | JSON Schema validation for single product and list, ResponseSpec |

## Key Techniques

- **Data-Table Assertion Engine** — single reusable step drives 11 matcher types from a Cucumber DataTable
- **JSON Schema Validation** — contract testing with `matchesJsonSchemaInClasspath()`
- **Response Spec** — `ResponseSpecBuilder` for DRY response validation
- **Scenario Outline** — data-driven tests via Examples tables
- **Response Time** — assertions with `time(lessThan(...))`
- **Request/Response Logging** — `RequestLoggingFilter` + `ResponseLoggingFilter` on all services

## Run Tests

```bash
# All suites
mvn clean verify

# Single suite
mvn test -DtestSuite=QueryParamsRunner
```

Available runners: `ProductApiRunner`, `BrandApiRunner`, `AuthApiRunner`, `UserAccountApiRunner`, `TokenAuthApiRunner`, `QueryParamsRunner`, `SortingRunner`, `HamcrestAssertionsRunner`, `SchemaValidationRunner`

## Reports

[Live Cucumber Report](https://jgupta-git.github.io/restapi-automation-java-restassured-junit/) — hosted on GitHub Pages

Reports are generated at `target/cucumber-html-reports/` via `maven-cucumber-reporting` plugin. Jenkins post-build uses Publish HTML Reports.

## Project Guide

See [restassured-cucumber-project-guide.html](restassured-cucumber-project-guide.html) or the [PDF version](restassured-cucumber-project-guide.pdf) for full architecture and code patterns.
