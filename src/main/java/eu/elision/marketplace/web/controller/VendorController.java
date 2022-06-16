package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.logic.services.vat.Business;
import eu.elision.marketplace.web.dtos.TokenDto;
import eu.elision.marketplace.web.dtos.users.VendorPageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Controller to handle calls about the vendor
 */
@RestController()
@RequestMapping("/vendor")
public class VendorController
{
    final
    Controller controller;

    /**
     * Public constructor
     *
     * @param controller the controller that needs to be used
     */
    public VendorController(Controller controller)
    {
        this.controller = controller;
    }

    @GetMapping("/vat/{vat}")
    @ResponseBody
    ResponseEntity<Business> checkVat(@PathVariable("vat") String vat)
    {
        return new ResponseEntity<>(controller.checkVat(vat), HttpStatus.OK);
    }

    /**
     * Get the info of a vendor for the vendor page
     *
     * @param id the id of the vendor
     * @return a response entity with the vendor page dto
     */
    @GetMapping("/{id}")
    public ResponseEntity<VendorPageDto> getVendorPageInfo(@PathVariable("id") long id)
    {
        return ResponseEntity.ok(controller.findVendorById(id));
    }

    /**
     * Renew the API token of a vendor and return the new token
     *
     * @param principal vendor
     * @return string of the new token
     */
    @PostMapping("/renewToken")
    @Secured("ROLE_VENDOR")
    ResponseEntity<TokenDto> refreshToken(Principal principal)
    {
        return ResponseEntity.ok(controller.getVendorToken(principal.getName()));
    }
}
