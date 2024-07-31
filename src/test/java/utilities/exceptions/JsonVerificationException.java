package utilities.exceptions;


/**
 * Custom exception for JSON verification errors.
 */
public class JsonVerificationException extends RuntimeException {
    /**
     * Constructs a new JsonVerificationException with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   the cause of the exception.
     */
    public JsonVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
