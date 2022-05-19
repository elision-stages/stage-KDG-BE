package eu.elision.marketplace.web.webexceptions;

/**
 * An exception used when invalid data is used to create an object
 */
public class InvalidDataException extends RuntimeException
{
    /**
     * Create a new invalid data exception with a message
     *
     * @param message the message that the exception needs to pass
     */
    public InvalidDataException(String message)
    {
        super(message);
    }
}
