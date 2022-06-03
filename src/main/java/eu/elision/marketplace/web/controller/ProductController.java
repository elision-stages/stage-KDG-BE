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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

/**
 * Rest controller to handle calls about products
 */
@RestController
public class ProductController {
    final Controller controller;
    final UserService userService;
    private static final String SUCCESS = "success";
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    AlgoliaIndexerService algoliaIndexerService;

    /**
     * Public constructor
     *
     * @param controller            the controller that the rest controller needs to use
     * @param userService           the user service that needs to be used
     * @param bCryptPasswordEncoder An BCryptPasswordEncoder instance
     */
    public ProductController(Controller controller, UserService userService, AlgoliaIndexerService algoliaIndexerService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.controller = controller;
        this.userService = userService;
        this.algoliaIndexerService = algoliaIndexerService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /**
     * Adds a product to the vendor's product list by principal OR API data
     *
     * @param principal  Principal of the vendor
     * @param apiName    Mail of the vendor (when inserting via API)
     * @param apiToken   API token of the vondor (when inserting via API)
     * @param productDto Product DTO to insert
     * @return Success DTO or unauthorized if authorization fails
     */
    @PostMapping("/addProduct")
    ResponseEntity<?> addProduct(Principal principal, @RequestHeader(value = "X-API-User", required = false) String apiName, @RequestHeader(value = "X-API-Key", required = false) String apiToken, @RequestBody ProductDto productDto) {
        if (principal != null) {
            UserDetails user = userService.loadUserByUsername(principal.getName());
            if (user instanceof Vendor) {
                Product newProduct = controller.saveProduct((Vendor) user, productDto);
                algoliaIndexerService.indexProduct(newProduct);
                return ResponseEntity.ok(new ResponseDto(SUCCESS));
            }
        } else if (apiName != null && apiToken != null) {
            UserDetails user = userService.loadUserByUsername(apiName);
            if (user instanceof Vendor vendor) {
                if (bCryptPasswordEncoder.matches(apiToken, vendor.getToken())) {
                    Product newProduct = controller.saveProduct(vendor, productDto);
                    algoliaIndexerService.indexProduct(newProduct);
                    return ResponseEntity.ok(new ResponseDto(SUCCESS));
                }
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/getAllProducts")
    ResponseEntity<Collection<Product>> getAllProducts() {
        return new ResponseEntity<>(controller.findAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/getMyProducts")
    @Secured("ROLE_VENDOR")
    ResponseEntity<Collection<SmallProductDto>> getMyProducts(Principal principal) {
        Vendor vendor = (Vendor) userService.loadUserByUsername(principal.getName());
        Collection<Product> products = controller.findProductsByVendor(vendor);

        return new ResponseEntity<>(products.stream().map(Mapper::toSmallProductDto).toList(), HttpStatus.OK);
    }

    @GetMapping("/product/{ids}")
    ResponseEntity<Product> getProduct(@PathVariable String ids) {
        long id = Long.parseLong(ids);
        Product product = controller.findProduct(id);
        if (product == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Secured("ROLE_VENDOR")
    @PostMapping("/deleteProduct/{id}")
    ResponseEntity<ResponseDto> deleteProduct(@PathVariable String id, Principal principal) {
        controller.deleteProduct(Long.parseLong(id), principal.getName());
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

    @Secured("ROLE_VENDOR")
    @PostMapping("/editProduct")
    ResponseEntity<ResponseDto> editProduct(@RequestBody EditProductDto editProductDto, Principal principal) {
        Product product = controller.editProduct(editProductDto, principal.getName());

        algoliaIndexerService.indexProduct(product);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

}
