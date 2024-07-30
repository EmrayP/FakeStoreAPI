# Fake Store API Product Filters

This project is a Java-based framework that uses Maven for build automation, RestAssured for API testing, and Cucumber for behavior-driven development (BDD). The project is configured to run tests and generate reports, which are then deployed to GitHub Pages.

## Prerequisites

- Java 11
- Maven
- Node.js
- Git

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/EmrayP/FakeStoreAPI.git
    ```

2. Install dependencies:
    ```sh
    mvn clean install
    ```

3. Configure the base URL in `ConfigurationsReader`:
    ```java
    RestAssured.baseURI = ConfigurationsReader.getProperty(FrameworkConstants.BASE_URL);
    ```

## Running Tests

1. Run the tests using Maven:
    ```sh
    mvn test
    ```

2. Generate Cucumber reports:
    ```sh
    mvn test -Dcucumber.options="--plugin json:target/cucumber.json"
    ```

## GitHub Actions

The project is configured to use GitHub Actions for continuous integration and deployment. The workflow file is located at `.github/workflows/ci.yml`.

### Workflow Steps

1. **Checkout repository**: Checks out the repository under `$GITHUB_WORKSPACE`.
2. **Set up Node.js**: Sets up Node.js version 20.
3. **Set up JDK 11**: Sets up Java Development Kit (JDK) version 11.
4. **Build with Maven**: Runs `mvn clean verify` to build the project.
5. **Run Cucumber Tests**: Executes the Cucumber tests.
6. **Generate Cucumber Report**: Uses `deblockt/cucumber-report-annotations-action` to generate the report.
7. **Deploy to GitHub Pages**: Uses `peaceiris/actions-gh-pages` to deploy the report to GitHub Pages.

## Deployment

The deployment is handled by GitHub Actions and the report is published to GitHub Pages. Ensure that the `secrets.Token` is correctly configured in your repository settings.

