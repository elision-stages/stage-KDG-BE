package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.services.AlgoliaIndexerService;
import eu.elision.marketplace.web.dtos.VendorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlgoliaController {
    private AlgoliaIndexerService algoliaIndexerService;

    public AlgoliaController(AlgoliaIndexerService algoliaIndexerService) {
        this.algoliaIndexerService = algoliaIndexerService;
    }

    @PostMapping("/updatealgolia")
    ResponseEntity<String> registerVendor(@RequestBody VendorDto vendorDto) {
        algoliaIndexerService.indexAllProducts();
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }
}
