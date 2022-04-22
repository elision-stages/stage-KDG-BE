package eu.elision.marketplace.web.webexceptions;

public class NotFoundException extends RuntimeException
{
    public NotFoundException(){
        super("not found");
    }
}
