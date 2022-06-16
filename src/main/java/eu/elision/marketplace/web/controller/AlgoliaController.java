package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for calls related to algolia
 */
@RestController
public class AlgoliaController
{
    private final Controller controller;

    /**
     * Public constructor
     *
     * @param controller the controller that the controller needs to use
     */
    public AlgoliaController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("/updatealgolia")
    ResponseEntity<String> updateAlgolia()
    {
        controller.indexAllProducts();
        return ResponseEntity.ok("{\"status\": \"ok\"}");
    }
}
