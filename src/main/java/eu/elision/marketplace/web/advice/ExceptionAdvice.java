package eu.elision.marketplace.web.advice;

import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.webexceptions.InvalidDataException;
import eu.elision.marketplace.web.webexceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception handler for the controllers
 */
@ControllerAdvice
public class ExceptionAdvice
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
    ResponseEntity<ResponseDto> notFoundHandler(NotFoundException ex)
    {
        return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Web exception handler for InvalidDataException
     *
     * @param ex the InvalidDataException that is thrown
     * @return the message of the exception
     */
    @ResponseBody
    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ResponseDto> invalidDataHandler(InvalidDataException ex)
    {
        return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }
}
