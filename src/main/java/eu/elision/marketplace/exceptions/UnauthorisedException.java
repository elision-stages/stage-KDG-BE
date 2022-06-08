package eu.elision.marketplace.exceptions;

/**
 * An exception used for unauthorised access request.
 */
public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String message) {
        super(message);
    }
}
