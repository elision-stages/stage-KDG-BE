package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.services.algolia.AlgoliaIndexerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for calls related to algolia
 */
@RestController
public class AlgoliaController
{
    private final AlgoliaIndexerService algoliaIndexerService;

    /**
     * Public constructor
     *
     * @param algoliaIndexerService the indexer that the controller needs to use
     */
    public AlgoliaController(AlgoliaIndexerService algoliaIndexerService)
    {
        this.algoliaIndexerService = algoliaIndexerService;
    }

    @PostMapping("/updatealgolia")
    ResponseEntity<String> updateAlgolia()
    {
        algoliaIndexerService.indexAllProducts();
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }
}
