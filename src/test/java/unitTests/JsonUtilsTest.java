package unitTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utilities.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the JsonUtils class.
 * This class tests only the implemented methods in JsonUtils:
 * - writeToJsonFile
 * - verifyJsonFileContent
 * - handleError
 */
public class JsonUtilsTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private List<JsonUtils.Product> products;
    private File tempFile;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        products = new ArrayList<>();
        JsonUtils.Product product1 = new JsonUtils.Product();
        product1.setPrice(19.99);
        JsonUtils.Rating rating1 = new JsonUtils.Rating();
        rating1.rate = 4.5;
        rating1.count = 150;
        product1.setRating(rating1);

        JsonUtils.Product product2 = new JsonUtils.Product();
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

    @Test
    public void testWriteToJsonFile() {
        JsonUtils.writeToJsonFile(products, tempFile.getPath());
        assertTrue(tempFile.exists(), "File should be created");
        assertTrue(tempFile.length() > 0, "File should not be empty");

        try {
            List<JsonUtils.Product> writtenProducts = objectMapper.readValue(tempFile, objectMapper.getTypeFactory().constructCollectionType(List.class, JsonUtils.Product.class));
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

    @Test
    public void testVerifyJsonFileContent() throws IOException {
        JsonUtils.writeToJsonFile(products, tempFile.getPath());
        JsonUtils.verifyJsonFileContent(tempFile.getPath(), 3.0, 100);
    }

    @Test
    public void testHandleError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            throw new RuntimeException("Test exception");
        });
        assertEquals("Test exception", exception.getMessage());
    }
}