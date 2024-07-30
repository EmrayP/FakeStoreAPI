package utilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.messages.types.Product;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonUtils {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        // Method to write object to JSON file
        public static void writeToJsonFile(Object object, String fileName) {
            try (FileWriter file = new FileWriter(fileName)) {
                String jsonString = objectMapper.writeValueAsString(object);
                file.write(jsonString);
            } catch (IOException e) {
                handleError(e);
            }
        }

        // Error handling method
        public static void handleError(Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }


        public static void verifyJsonFileContent(String fileName, double minRating, int minReviews) throws IOException {
            File file = new File(fileName);
            Assertions.assertTrue(file.exists(), "The file should be created.");
            Assertions.assertTrue(file.length() > 0, "The file should not be empty.");

            List<Product> writtenProducts = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            for (Product product : writtenProducts) {
                Assertions.assertTrue(product.getRating().rate >= minRating, "Product rating should be at least " + minRating);
                Assertions.assertTrue(product.getRating().count >= minReviews, "Product review count should be at least " + minReviews);
                Assertions.assertTrue(product.getPrice() >= 0, "Price should be non-negative.");
            }

            // Clean up the test file
            file.delete();
        }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private String id;
        private String title;
        private double price;
        private Rating rating;

        // No-argument constructor
        public Product() {}

        // Getters and Setters for all fields
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }

        public Rating getRating() { return rating; }
        public void setRating(Rating rating) { this.rating = rating; }
    }

    public static class Rating {
        public double rate;
        public int count;
    }
    }

