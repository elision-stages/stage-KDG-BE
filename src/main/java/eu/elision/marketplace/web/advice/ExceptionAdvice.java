package eu.elision.marketplace.web.advice;

import eu.elision.marketplace.exceptions.InvalidDataException;
import eu.elision.marketplace.exceptions.NotFoundException;
import eu.elision.marketplace.exceptions.UnauthorisedException;
import eu.elision.marketplace.web.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

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
    ResponseEntity<ResponseDto> invalidDataHandler(InvalidDataException ex) {
        return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

    /**
     * Web exception handler for InvalidDataException
     *
     * @param ex the InvalidDataException that is thrown
     * @return the message of the exception
     */
    @ResponseBody
    @ExceptionHandler(UnauthorisedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    ResponseEntity<ResponseDto> unauthorisedHandler(UnauthorisedException ex)
    {
        return new ResponseEntity<>(new ResponseDto(ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    Map<String, String> handleValidationExceptions(ConstraintViolationException ex)
    {
        Map<String, String> errors = new HashMap<>();

        for (var constraintViolation : ex.getConstraintViolations())
        {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

        return errors;
    }
}
