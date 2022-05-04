package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.DynamicAttributeDto;
import eu.elision.marketplace.web.dtos.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ProductController {

    final Controller controller;

    public ProductController(Controller controller) {
        this.controller = controller;
    }

    @PostMapping("/addProduct")
    ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
        controller.saveProduct(productDto);
        return ResponseEntity.ok("\"status\":\"success\"");
    }

    @GetMapping("/getAllProducts")
    ResponseEntity<Collection<Product>> getAllProducts() {
        return new ResponseEntity<>(controller.findAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/addAttribute")
    ResponseEntity<String> addAttribute(@RequestBody DynamicAttributeDto dynamicAttributeDto) {
        controller.saveDynamicAttribute(dynamicAttributeDto);
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }
}
