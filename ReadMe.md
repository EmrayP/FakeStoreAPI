# Fake Store API Product Filters

This project is a Java-based framework that uses Maven for build automation, RestAssured for API testing, and Cucumber for behavior-driven development (BDD) written with Gherkin. The project is configured to run tests and generate reports, which are then deployed to GitHub Pages.

## Prerequisites

- Java 11
- Maven
- Cucumber
- Node.js
- Git
- Postman (for manual API testing)

## Setup

1. Clone the repository:
    ```sh
    git clone https://github.com/EmrayP/FakeStoreAPI.git
    ```

2. Install dependencies:
    ```sh
    mvn clean install
    ```

3. Configure the base URL in `config.properties`:
   - Open the `config.properties` file located in the `src/main/resources` directory.
   - Set the `baseURL` property to the appropriate value:
       ```properties
       baseURL=https://your-api-base-url.com
       ```

## Running Tests

1. Run the tests using Maven:
    ```sh
    mvn clean test
    ```

### Running Unit Tests Manually in IntelliJ IDEA
1. Open IntelliJ IDEA.
2. Navigate to the `src/test/java` directory in the Project view.
3. Right-click on the `unitTests.JsonUtilsTest` class.
4. Select `Run 'JsonUtilsTest'` to execute the tests.

### Running Tests in the Terminal
1. Open a terminal window.
2. Navigate to the root directory of your project.
3. Run the following command to execute all tests using Maven:
    ```sh
    mvn clean test
    ```

### Running Cucumber Feature Tests
1. Open a terminal window.
2. Navigate to the root directory of your project.
3. Run the following command to execute all Cucumber feature tests using Maven:
    ```sh
    mvn test -Dcucumber.options="src/test/resources/features"
    ```

## Running Tests in CI/CD with GitHub Actions

GitHub Actions is configured to run tests based on pull requests (PRs). Whenever a PR happens, GitHub Actions will pick up the code changes.

1. Navigate to the **Actions** tab in the GitHub repository.
2. Search for the workflow named `CI: Java CI with Maven-Cucumber` to see the test results.
   ![GitHub Actions](images/github-actions.png)
3. If you want to manually trigger the CI, click the **Run workflow** button on the right-hand side.
   ![Run Workflow](images/run-workflow.png)

## Manual API Testing with Postman

You can manually test the APIs using the provided Postman collection.

1. Open Postman.
2. Import the Postman collection from the `postman` directory of this repository.
3. Configure the environment variables as needed.
4. Run the API requests manually.

### Adding Test Scripts in Postman

To add test scripts in Postman, follow these steps:

1. Open the Postman app.
2. Go to the `Tests` tab for a specific request.
3. Test scripts are written to verify requirements.
4. Save the request and run it to see the test results.

## Generating Reports

After running the tests, you can generate Cucumber reports. These reports will be available in the `target/cucumber-report-html` directory.

To view the reports, open the `index.html` file in a web browser.

## Additional Information

- Ensure you have configured the base URL and other necessary configurations in the `config.properties` file.
- Use the provided Postman collection for manual testing and verification of API endpoints.
- Check the generated Cucumber reports for detailed test execution results and statistics.

For more information and troubleshooting, refer to the project documentation and resources.
