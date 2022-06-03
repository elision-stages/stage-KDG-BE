package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.web.api.vat.Business;
import eu.elision.marketplace.web.api.vat.VATClient;
import eu.elision.marketplace.web.dtos.users.VendorPageDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to handle calls about the vendor
 */
@RestController()
@RequestMapping("/vendor")
public class VendorController
{
    VATClient vatClient = new VATClient();
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
        Business result = vatClient.checkVatService(vat);

        if (result == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
        return ResponseEntity.ok(controller.getVendorById(id));
    }
}
