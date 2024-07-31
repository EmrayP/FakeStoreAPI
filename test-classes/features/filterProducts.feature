Feature: Filter and Format Highly Rated Products

  Background:
    Given the application is configured to connect to base url
    And the application can retrieve the products without error

  Scenario: Filter and Format Highly Rated Products
    Given the application requests all products from the API
    When the application receives the list of products
    Then it should filter out products with a rating less than 3.0 or fewer than 100 reviews
    And it should format the price of each filtered product into USD currency format
    And it should write the filtered products to Json-file
    And the output.folder should contain the file filtered and formatted products
