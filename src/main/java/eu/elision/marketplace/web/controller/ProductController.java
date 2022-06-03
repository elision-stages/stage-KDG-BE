package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.logic.services.algolia.AlgoliaIndexerService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import eu.elision.marketplace.web.dtos.product.SmallProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

/**
 * Rest controller to handle calls about products
 */
@RestController
public class ProductController
{
    final Controller controller;
    final UserService userService;
    private static final String SUCCESS = "success";

    AlgoliaIndexerService algoliaIndexerService;

    /**
     * Public constructor
     *
     * @param controller            the controller that the rest controller needs to use
     * @param userService           the user service that needs to be used
     * @param algoliaIndexerService the algolia index service that needs to be used
     */
    public ProductController(Controller controller, UserService userService, AlgoliaIndexerService algoliaIndexerService)
    {
        this.controller = controller;
        this.userService = userService;
        this.algoliaIndexerService = algoliaIndexerService;
    }

    /**
     * Adds a product to the vendor's product list by principal OR API data
     *
     * @param principal     Principal of the vendor
     * @param apiVendorMail Mail of the vendor (when inserting via API)
     * @param apiToken      API token of the vondor (when inserting via API)
     * @param productDto    Product DTO to insert
     * @return Success DTO or unauthorized if authorization fails
     */
    @PostMapping("/addProduct")
    ResponseEntity<ResponseDto> addProduct(Principal principal, @RequestHeader(value = "X-API-User", required = false) String apiVendorMail, @RequestHeader(value = "X-API-Key", required = false) String apiToken, @RequestBody ProductDto productDto)
    {
        controller.saveProduct(principal != null ? principal.getName() : apiVendorMail, productDto, apiToken);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

    @GetMapping("/getAllProducts")
    ResponseEntity<Collection<Product>> getAllProducts()
    {
        return new ResponseEntity<>(controller.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/getMyProducts")
    @Secured("ROLE_VENDOR")
    ResponseEntity<Collection<SmallProductDto>> getMyProducts(Principal principal)
    {
        Vendor vendor = (Vendor) userService.loadUserByUsername(principal.getName());
        Collection<Product> products = controller.findProductsByVendor(vendor);

        return new ResponseEntity<>(products.stream().map(Mapper::toSmallProductDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/product/{ids}")
    ResponseEntity<Product> getProduct(@PathVariable String ids)
    {
        long id = Long.parseLong(ids);
        Product product = controller.findProduct(id);
        if (product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Secured("ROLE_VENDOR")
    @PostMapping("/deleteProduct/{id}")
    ResponseEntity<ResponseDto> deleteProduct(@PathVariable String id, Principal principal)
    {
        controller.deleteProduct(Long.parseLong(id), principal.getName());
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

    @Secured("ROLE_VENDOR")
    @PostMapping("/editProduct")
    ResponseEntity<ResponseDto> editProduct(@RequestBody EditProductDto editProductDto, Principal principal)
    {
        Product product = controller.editProduct(editProductDto, principal.getName());

        algoliaIndexerService.indexProduct(product);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

}
