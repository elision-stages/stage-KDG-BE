package eu.elision.marketplace.exceptions;

/**
 * Exception used when something is not found
 */
public class NotFoundException extends RuntimeException
{
    /**
     * Create standard not found exception with message 'not found'
     */
    public NotFoundException()
    {
        super("not found");
    }

    public NotFoundException(String message)
    {
        super(message);
    }
}
