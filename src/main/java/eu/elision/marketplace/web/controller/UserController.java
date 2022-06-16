package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest controller to handle calls about users
 */
@RestController
@RequestMapping("register")
public class UserController
{
    final
    Controller controller;

    /**
     * Public constructor
     *
     * @param controller the controller that needs to be used
     */
    public UserController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("customer")
    ResponseEntity<ResponseDto> registerCustomer(@RequestBody @Valid CustomerDto customerDto)
    {
        controller.saveCustomer(customerDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }

    @PostMapping("vendor")
    ResponseEntity<ResponseDto> registerVendor(@RequestBody VendorDto vendorDto) {
        controller.saveVendor(vendorDto);
        return ResponseEntity.ok(new ResponseDto("success"));
    }

}
