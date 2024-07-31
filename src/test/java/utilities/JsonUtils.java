package utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import pages.Product;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static utilities.constants.Messages.RESULT_JSON_FILE_CREATED_MESSAGE;
import static utilities.constants.Messages.RESULT_JSON_FILE_EMPTY_MESSAGE;
import static utilities.constants.Messages.PRODUCT_RATING_MESSAGE;
import static utilities.constants.Messages.PRODUCT_REVIEW_COUNT_MESSAGE;
import static utilities.constants.Messages.PRICE;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This method serializes a given Java object into a JSON string and writes it to a file within a specified folder.
     *
     * @param object The Java object to be serialized into JSON.
     * @param folderName The name of the folder where the JSON file will be stored.
     * @param fileName The name of the JSON file to be created.
     *
     * The method performs the following steps:
     * 1. Checks if the specified folder exists. If it does not exist, it creates the folder.
     * 2. Creates a new file within the specified folder using the provided fileName.
     * 3. Uses a FileWriter to write the JSON string representation of the object to the file.
     * 4. Utilizes the ObjectMapper from the Jackson library to convert the Java object into a JSON string.
     * 5. Handles any IOException that occurs during the process by calling the handleError method.
     */
    public static void writeToJsonFile(Object object, String folderName, String fileName) {
        try {
            // Create the folder if it doesn't exist
            File folder = new File(folderName);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Create the file within the folder
            File file = new File(folder, fileName);
            try (FileWriter fileWriter = new FileWriter(file)) {
                String jsonString = objectMapper.writeValueAsString(object);
                fileWriter.write(jsonString);
            }
        } catch (IOException e) {
            handleError(e);
        }
    }

    // Error handling method
    public static void handleError(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * This method verifies the content of a JSON file by checking if it exists, is not empty, and contains products
     * that meet specified rating and review count criteria.
     *
     * @param folderName The name of the folder where the JSON file is stored.
     * @param fileName The name of the JSON file to be verified.
     * @param minRating The minimum rating threshold for the products.
     * @param minReviews The minimum review count threshold for the products.
     * @throws IOException If an I/O error occurs during file reading.
     *
     * The method performs the following steps:
     * 1. Checks if the specified file exists and is not empty.
     * 2. Reads the JSON content from the file and deserializes it into a list of Product objects.
     * 3. Iterates through the list of products and verifies that each product meets the specified rating and review count criteria.
     * 4. Deletes the file after verification.
     */
    public static void verifyJsonFileContent(String folderName, String fileName, double minRating, int minReviews) throws IOException {
        File file = new File(folderName, fileName);
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


    public static class Rating {
        public double rate;
        public int count;
    }
}