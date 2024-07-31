package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import utilities.ConfigurationsReader;
import utilities.JsonUtils;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import utilities.constants.FrameworkConstants;
import utilities.constants.StatusCode;
import utilities.constants.Messages;
import utilities.exceptions.JsonVerificationException;

import static utilities.constants.Messages.*;


/**
 * This class contains the step definitions for the Cucumber feature file `filterProducts.feature`.
 * It includes methods to configure the application, retrieve products, filter and format them,
 * and write the results to a JSON file.
 * <p>
 * Author: Emray Pala
 * Created Date: 2024-07-30
 */
public class FilterProductsSteps {
    private Response response;
    private List<pages.Product> filteredProducts;


    @Given("the application is configured to connect to base url")
    public void the_application_is_configured_to_connect_to_base_url() {
        RestAssured.baseURI = ConfigurationsReader.getProperty(FrameworkConstants.BASE_URL);
    }

    @Given("the application can retrieve the products without error")
    public void the_application_can_retrieve_the_products_without_error() {
        response = RestAssured.get();
        if (response.getStatusCode() == StatusCode.SERVER_DOESNT_REPLY) {
            throw new RuntimeException(Messages.SERVER_DOESNT_REPLY);
        } else {
            Assert.assertEquals(StatusCode.OK, response.getStatusCode());
        }
    }

    @Given("the application requests all products from the API")
    public void the_application_requests_all_products_from_the_API() {
        response = RestAssured.get();
    }

    @When("the application receives the list of products")
    public void the_application_receives_the_list_of_products() {
        pages.Product[] products = response.getBody().as(pages.Product[].class);
        filteredProducts = new ArrayList<>();
        for (pages.Product product : products) {
            if (product.getRating().rate >= FrameworkConstants.RATE_THRESHOLD && product.getRating().count >= FrameworkConstants.COUNT_THRESHOLD) {
                filteredProducts.add(product);
            }
        }
    }

    @Then("it should filter out products with a rating less than {double} or fewer than {int} reviews")
    public void it_should_filter_out_products_with_a_rating_less_than_or_fewer_than_reviews(Double rating, Integer reviews) {
        for (pages.Product product : filteredProducts) {
            Assertions.assertTrue(product.getRating().rate >= rating, PRODUCT_RATING_MESSAGE + rating);
            Assertions.assertTrue(product.getRating().count >= reviews, PRODUCT_REVIEW_COUNT_MESSAGE + reviews);
        }
    }

    @Then("it should format the price of each filtered product into USD currency format")
    public void it_should_format_the_price_of_each_filtered_product_into_USD_currency_format() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (pages.Product product : filteredProducts) {
            product.setPrice(Double.parseDouble(currencyFormatter.format(product.getPrice()).replaceAll("[^\\d.]", "")));
        }
    }

    @Then("it should write the filtered products to Json-file")
    public void it_should_write_the_filtered_products_to_json_file() {
        JsonUtils.writeToJsonFile(filteredProducts, FrameworkConstants.RESULT_JSON_FILE);

    }

    @Then("the results.json file should contain the filtered and formatted products")
    public void the_results_json_file_should_contain_the_filtered_and_formatted_products() {
        try {
            JsonUtils.verifyJsonFileContent(FrameworkConstants.RESULT_JSON_FILE, FrameworkConstants.RATE_THRESHOLD, FrameworkConstants.COUNT_THRESHOLD);
        } catch (IOException e) {
            throw new JsonVerificationException(RUN_TIME_EXCEPTION, e);
        }
    }
}