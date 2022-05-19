package eu.elision.marketplace.logic.converter.exeption;

/**
 * Exception that gets thrown when something goes wrong with the conversion
 */
public class ConversionException extends RuntimeException {
    public ConversionException(String message) {
        super(message);
    }
}
