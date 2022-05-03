package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.web.api.vat.Business;
import eu.elision.marketplace.web.api.vat.VATClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/vendor")
public class VendorController {
    VATClient vatClient = new VATClient();

    @GetMapping("/vat/{vat}")
    @ResponseBody
    ResponseEntity<Business> vat(@PathVariable("vat") String vat)
    {
        vat = vat.replaceAll("[^a-zA-Z0-9]", "");
        if (vat.length() < 10) return new ResponseEntity<>(HttpStatus.NOT_FOUND);// Save some bandwidth

        Business result = vatClient.checkVatService(vat);

        if (result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
