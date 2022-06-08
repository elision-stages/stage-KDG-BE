package eu.elision.marketplace.exceptions;

/**
 * Exception that gets thrown when something goes wrong with the conversion
 */
public class ConversionException extends RuntimeException {
    public ConversionException(String message) {
        super(message);
    }
}
