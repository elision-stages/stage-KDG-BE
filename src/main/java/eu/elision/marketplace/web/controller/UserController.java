package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.CustomerDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController
{
    final
    Controller controller;

    public UserController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("/register")
    void registerCustomer(@RequestBody CustomerDto customerDto)
    {
        controller.saveCustomer(customerDto);
    }

    @GetMapping("/allUsers")
    List<CustomerDto> findAllUsers()
    {
        return controller.findAllCustomerDto();
    }
}
