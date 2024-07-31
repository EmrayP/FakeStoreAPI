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
     baseURL=https://fakestoreapi.com
       ```

## Running Tests

Run the tests using Maven:
    ```
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
   
3. If you want to manually trigger the CI, click the **Run workflow** button on the right-hand side.
## Viewing the Report
1. Navigate to the Actions tab on your GitHub repository.
2. Search for the workflow run named "Java CI with Maven-Cucumber".
3. Click on the run to see the details.
4. If you want to manually trigger the CI, click "Run workflow" on the right-hand side.
5. After the workflow completes, the HTML report will be available under the gh-pages branch.

  
## Manual API Testing with Postman
You can manually test the APIs using the provided Postman collection.
# How to do:
1. Open Postman.
2. Import the Postman collection `Product Test-FakeStoreAPI.postman_collection.json` from the directory of this repository.
3. The collection contains requests to test the Fake Store API endpoints.
4. API method is 'GET'
5. Hit on "Send" button and run the API requests manually.
6. Verify the response data and status code.

### Adding Test Scripts in Postman

To add test scripts in Postman, follow these steps:
1. Open the Postman app.
2. Go to the `Tests` tab for a specific request.
3. Test scripts are written to verify requirements.
4. Save the request and run it to see the test results.

## Generating Reports
After running the tests, you can generate Cucumber reports. These reports will be available in the `target/Cucumber-Reports` directory.
To view the reports, open the `Cucumber-html-report.html` file in a web browser.

## Interpreting the Results
### Test Output

After running the tests, Maven will display the test results in the terminal. Look for lines indicating the number of tests run, passed, failed, and skipped.

Example:

[INFO] Results:

[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0

### Detailed Report

Maven generates a detailed test report in the `target/surefire-reports` directory. Open the `index.html` file in a web browser to view the detailed test results, including:

- Test cases executed
- Pass/fail status
- Error messages and stack traces for any failed tests

### JUnit Assertions

Pay attention to the assertions in the test methods. If an assertion fails, it indicates that the expected condition was not met, and the test will fail. Review the assertion messages to understand the cause of the failure.

### Error Handling

If any exceptions are thrown during the test execution, they will be logged in the test report. Review the stack traces to identify and fix the issues in the code.

By following these steps, you can execute the tests and interpret the results to ensure your application is functioning as expected.

### Troubleshooting
If the artifacts are not getting created, ensure the following:

1. The target directory exists and contains the necessary report files.
2. The actions/upload-artifact@v4 step is correctly configured.
3. The peaceiris/actions-gh-pages@v4 step is correctly configured with the correct publish_dir.

### Hints
1. Make sure the pom.xml dependencies have the required versions.
2. You can update the IDE version to get the latest updates.
3. Make sure the GitHub Actions workflow file is correctly configured.
## Additional Information

- Ensure you have configured the base URL and other necessary configurations in the `config.properties` file.
- Use the provided Postman collection for manual testing and verification of API endpoints.
- Check the generated Cucumber reports for detailed test execution results and statistics.

