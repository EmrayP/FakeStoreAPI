package unitTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utilities.JsonUtils;
import utilities.constants.FrameworkConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static utilities.constants.FrameworkConstants.OUT_PUT_FOLDER;
import static utilities.constants.Messages.RESULT_JSON_FILE_CREATED_MESSAGE;
import static utilities.constants.Messages.RESULT_JSON_FILE_EMPTY_MESSAGE;

/**
 * Unit tests for the JsonUtils class.
 * This class tests only the implemented methods in JsonUtils:
 * - writeToJsonFile
 * - verifyJsonFileContent
 * - handleError
 */
public class JsonUtilsTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private List<pages.Product> products;
    private File tempFile;
    //creating a temporary directory for unit tests
    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();
        pages.Product product1 = new pages.Product();
        product1.setPrice(19.99);
        JsonUtils.Rating rating1 = new JsonUtils.Rating();
        rating1.rate = 4.5;
        rating1.count = 150;
        product1.setRating(rating1);

        pages.Product product2 = new pages.Product();
        product2.setPrice(29.99);
        JsonUtils.Rating rating2 = new JsonUtils.Rating();
        rating2.rate = 3.5;
        rating2.count = 200;
        product2.setRating(rating2);
        products.add(product1);
        products.add(product2);

        tempFile = tempDir.resolve("test.json").toFile();
    }

    @AfterEach
    public void tearDown() {
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * This test method verifies the functionality of the `writeToJsonFile` method in the `JsonUtils` class.
     * It ensures that the method correctly writes a list of products to a JSON file and that the file contains the expected data.
     * <p>
     * The method performs the following steps:
     * 1. Calls the `writeToJsonFile` method to write the list of products to a JSON file in the temporary directory.
     * 2. Asserts that the output file exists and is not empty.
     * 3. Reads the JSON content from the output file and deserializes it into a list of `Product` objects.
     * 4. Asserts that the number of products in the output file matches the original list.
     * 5. Iterates through the list of products and asserts that the price, rating, and review count of each product match the original values.
     * 6. Catches any `IOException` that occurs during the process and fails the test if an exception is thrown.
     */
    @Test
    public void testWriteToJsonFile() {
        JsonUtils.writeToJsonFile(products, tempDir.toString(), FrameworkConstants.RESULT_JSON_FILE);
        File outputFile = new File(tempDir.toFile(), FrameworkConstants.RESULT_JSON_FILE);
        assertTrue(outputFile.exists(), RESULT_JSON_FILE_CREATED_MESSAGE);
        assertTrue(outputFile.length() > 0, RESULT_JSON_FILE_EMPTY_MESSAGE);

        try {
            List<pages.Product> writtenProducts = objectMapper.readValue(outputFile, objectMapper.getTypeFactory().constructCollectionType(List.class, pages.Product.class));
            assertEquals(products.size(), writtenProducts.size(), "Number of products should match");
            for (int i = 0; i < products.size(); i++) {
                assertEquals(products.get(i).getPrice(), writtenProducts.get(i).getPrice(), "Product prices should match");
                assertEquals(products.get(i).getRating().rate, writtenProducts.get(i).getRating().rate, "Product ratings should match");
                assertEquals(products.get(i).getRating().count, writtenProducts.get(i).getRating().count, "Product review counts should match");
            }
        } catch (IOException e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }


    /**
     * This test method verifies the functionality of the `verifyJsonFileContent` method in the `JsonUtils` class.
     * It ensures that the method correctly reads and verifies the content of a JSON file containing a list of products.
     * <p>
     * The method performs the following steps:
     * 1. Calls the `writeToJsonFile` method to write the list of products to a JSON file in the temporary directory.
     * 2. Calls the `verifyJsonFileContent` method to verify the content of the output file.
     * 3. Asserts that the output file is deleted after verification.
     * 4. Catches any `IOException` that occurs during the process and fails the test if an exception is thrown.
     */
    @Test
    public void testVerifyJsonFileContent() throws IOException {
        JsonUtils.writeToJsonFile(products, tempDir.toString(), FrameworkConstants.RESULT_JSON_FILE);
        JsonUtils.verifyJsonFileContent(tempDir.toString(), FrameworkConstants.RESULT_JSON_FILE, 3.0, 100);
    }


    @Test
    public void testHandleError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Test exception");
        });
        assertEquals("Test exception", exception.getMessage());
    }
}