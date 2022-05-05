package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ProductController
{

    final Controller controller;

    public ProductController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("/addProduct")
    ResponseEntity<String> addProduct(@RequestBody ProductDto productDto)
    {
        controller.saveProduct(productDto);
        return ResponseEntity.ok("\"status\":\"success\"");
    }

    @GetMapping("/getAllProducts")
    ResponseEntity<Collection<Product>> getAllProducts()
    {
        return new ResponseEntity<>(controller.findAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/addAttribute")
    ResponseEntity<String> addAttribute(@RequestBody DynamicAttributeDto dynamicAttributeDto)
    {
        controller.saveDynamicAttribute(dynamicAttributeDto);
        return ResponseEntity.ok("{\"status\":\"success\"}");
    }

    @GetMapping("/getCategories")
    ResponseEntity<Collection<CategoryDto>> getCategories()
    {
        return new ResponseEntity<>(controller.findAllCategoriesDto(), HttpStatus.OK);
    }

    @PostMapping("/addCategory")
    ResponseEntity<ResponseDto> addCategory(@RequestBody CategoryMakeDto categoryMakeDto)
    {
        controller.saveCategory(categoryMakeDto);
        return ResponseEntity.ok(new ResponseDto("status", "success"));
    }
}
