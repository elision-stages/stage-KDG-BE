package eu.elision.marketplace.web.webexceptions;

public class NotFoundException extends RuntimeException
{
    /**
     * Exception used when something is not found
     */
    public NotFoundException(){
        super("not found");
    }
}
