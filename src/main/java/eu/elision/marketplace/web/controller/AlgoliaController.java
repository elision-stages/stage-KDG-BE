package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.services.AlgoliaIndexerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AlgoliaController {
    private AlgoliaIndexerService algoliaIndexerService;

    public AlgoliaController(AlgoliaIndexerService algoliaIndexerService) {
        this.algoliaIndexerService = algoliaIndexerService;
    }

    @PostMapping("/updatealgolia")
    ResponseEntity<String> updateAlgolia() {
        algoliaIndexerService.indexAllProducts();
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }
}
