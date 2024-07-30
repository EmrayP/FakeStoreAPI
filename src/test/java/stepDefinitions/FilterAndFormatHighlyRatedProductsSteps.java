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


public class FilterAndFormatHighlyRatedProductsSteps {
    private Response response;
    private List<JsonUtils.Product> filteredProducts;
    private static final String RESULTS_FILE = "results.json";



    @Given("the application is configured to connect to {string}")
    public void the_application_is_configured_to_connect_to() {
        RestAssured.baseURI = ConfigurationsReader.getProperty("base.url");
    }

    @Given("the application can retrieve the products without error")
    public void the_application_can_retrieve_the_products_without_error() {
        response = RestAssured.get();
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Given("the application requests all products from the API")
    public void the_application_requests_all_products_from_the_API() {
        response = RestAssured.get();
    }

    @When("the application receives the list of products")
    public void the_application_receives_the_list_of_products() {
        JsonUtils.Product[] products = response.getBody().as(JsonUtils.Product[].class);
        filteredProducts = new ArrayList<>();
        for (JsonUtils.Product product : products) {
            if (product.getRating().rate >= 3.0 && product.getRating().count >= 100) {
                filteredProducts.add(product);
            }
        }
    }

    @Then("it should filter out products with a rating less than {double} or fewer than {int} reviews")
    public void it_should_filter_out_products_with_a_rating_less_than_or_fewer_than_reviews(Double rating, Integer reviews) {
        for (JsonUtils.Product product : filteredProducts) {
            Assertions.assertTrue(product.getRating().rate >= rating, "Product rating should be at least " + rating);
            Assertions.assertTrue(product.getRating().count >= reviews, "Product review count should be at least " + reviews);
        }
    }

    @Then("it should format the price of each filtered product into USD currency format")
    public void it_should_format_the_price_of_each_filtered_product_into_USD_currency_format() {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (JsonUtils.Product product : filteredProducts) {
            product.setPrice(Double.parseDouble(currencyFormatter.format(product.getPrice()).replaceAll("[^\\d.]", "")));
        }
    }



    @Then("it should write the filtered products to {string}")
    public void it_should_write_the_filtered_products_to(String fileName) {
        JsonUtils.writeToJsonFile(filteredProducts, fileName);
    }

    @Then("the results.json file should contain the filtered and formatted products")
    public void the_results_json_file_should_contain_the_filtered_and_formatted_products() throws IOException {
        JsonUtils.verifyJsonFileContent(RESULTS_FILE, 3.0, 100);
    }
}