package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("register")
public class UserController
{
    final
    Controller controller;

    public UserController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("customer")
    ResponseEntity<ResponseDto> registerCustomer(@RequestBody @Valid CustomerDto customerDto) {
        controller.saveCustomer(customerDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }

    @PostMapping("vendor")
    ResponseEntity<ResponseDto> registerVendor(@RequestBody VendorDto vendorDto) {
        controller.saveVendor(vendorDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationExceptions(ConstraintViolationException ex)
    {
        Map<String, String> errors = new HashMap<>();

        for (var constraintViolation : ex.getConstraintViolations())
        {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

        return errors;
    }
}
