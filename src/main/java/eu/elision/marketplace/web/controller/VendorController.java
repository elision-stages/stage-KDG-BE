package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.api.vat.Business;
import eu.elision.marketplace.web.api.vat.VATClient;
import eu.elision.marketplace.web.dtos.TokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController()
@RequestMapping("/vendor")
public class VendorController {
    VATClient vatClient = new VATClient();
    private final UserService userService;

    /**
     * Constructor of the Vendor Controller
     *
     * @param userService Autowired UserService
     */
    public VendorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/vat/{vat}")
    @ResponseBody
    ResponseEntity<Business> checkVat(@PathVariable("vat") String vat) {
        Business result = vatClient.checkVatService(vat);

        if (result == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Renew the API token of a vendor and return the new token
     *
     * @param principal vendor
     * @return string of the new token
     */
    @PostMapping("/renewToken")
    @Secured("ROLE_VENDOR")
    ResponseEntity<TokenDto> refreshToken(Principal principal) {
        Vendor vendor = (Vendor) userService.loadUserByUsername(principal.getName());
        return ResponseEntity.ok(new TokenDto(userService.updateToken(vendor)));
    }
}
