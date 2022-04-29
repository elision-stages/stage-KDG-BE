package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.VendorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController
{
    final
    Controller controller;

    public UserController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("/registercustomer")
    ResponseEntity<String> registerCustomer(@RequestBody @Valid CustomerDto customerDto) {
        controller.saveCustomer(customerDto);
        return ResponseEntity.ok("Customer is valid");
    }

    @PostMapping("/registervendor")
    void registerVendor(@RequestBody VendorDto vendorDto) {
        controller.saveVendor(vendorDto);
    }

    @GetMapping("/allUsers")
    List<User> findAllUsers() {
        return controller.findAllUsers();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        for (var constraintViolation : ex.getConstraintViolations()) {
            errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
        }

        return errors;
    }
}