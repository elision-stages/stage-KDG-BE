package eu.elision.marketplace.web.advice;

import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAdvice
{
    /**
     * An exception handler for the NotFoundException
     *
     * @param ex the exception that is thrown
     * @return the message of the exception
     */
    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String notFoundHandler(NotFoundException ex) {
        return ex.getMessage();
    }
}
