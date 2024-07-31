package utilities;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static constants.Messages.*;

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
        Assertions.assertTrue(file.exists(), RESULT_JSON_FILE_CREATED_MESSAGE);
        Assertions.assertTrue(file.length() > 0, RESULT_JSON_FILE_EMPTY_MESSAGE);

        try {
            List<Product> writtenProducts = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Product.class));
            for (Product product : writtenProducts) {
                Assertions.assertTrue(product.getRating().rate >= minRating, PRODUCT_RATING_MESSAGE + minRating);
                Assertions.assertTrue(product.getRating().count >= minReviews, PRODUCT_REVIEW_COUNT_MESSAGE + minReviews);
                Assertions.assertTrue(product.getPrice() >= 0, PRICE);
            }
        } finally {
            // Clean up the test file
            file.delete();
        }
    }

    /**
     * Represents a product with a price and rating.
     * Uses Lombok to generate getters, setters, and a no-argument constructor.
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {
        private double price;
        private Rating rating;
    }

    public static class Rating {
        public double rate;
        public int count;
    }
    }

