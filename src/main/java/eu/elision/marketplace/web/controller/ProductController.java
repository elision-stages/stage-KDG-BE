package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ProductController
{

    final Controller controller;
    static final String SUCCESS = "success";

    public ProductController(Controller controller)
    {
        this.controller = controller;
    }

    @PostMapping("/addProduct")
    ResponseEntity<ResponseDto> addProduct(@RequestBody ProductDto productDto)
    {
        controller.saveProduct(productDto);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

    @GetMapping("/getAllProducts")
    ResponseEntity<Collection<Product>> getAllProducts()
    {
        return new ResponseEntity<>(controller.findAllProducts(), HttpStatus.OK);
    }

    @PostMapping("/addCategory")
    ResponseEntity<ResponseDto> addCategory(@RequestBody CategoryMakeDto categoryMakeDto)
    {
        controller.saveCategory(categoryMakeDto);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

    @PostMapping("/editProduct")
    ResponseEntity<ResponseDto> editProduct(@RequestBody EditProductDto editProductDto)
    {
        controller.editProduct(editProductDto);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

}
