package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.api.vat.Business;
import eu.elision.marketplace.api.vat.VATClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;

@RestController()
@RequestMapping("/vendor")
public class VendorController {
    VATClient vatClient = new VATClient();

    @GetMapping("/vat/{vat}")
    @ResponseBody
    ResponseEntity<Business> vat(@PathVariable("vat") String vat) {
        vat = vat.replaceAll("[^a-zA-Z0-9]", "");
        if(vat.length() < 10) return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Save some bandwidth
        String country = vat.split("[0-9]")[0];
        String number = vat.substring(country.length());
        Business result = vatClient.checkVatService(country, number);
        if(result == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
