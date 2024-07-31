package pages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import utilities.JsonUtils;

/**
 * Represents a product with a price and rating.
 * Uses Lombok to generate getters, setters, and a no-argument constructor.
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private double price;
    private JsonUtils.Rating rating;
}