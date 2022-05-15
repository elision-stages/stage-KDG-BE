package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.services.UserService;
import eu.elision.marketplace.services.helpers.Mapper;
import eu.elision.marketplace.web.dtos.ResponseDto;
import eu.elision.marketplace.web.dtos.SmallProductDto;
import eu.elision.marketplace.web.dtos.category.CategoryMakeDto;
import eu.elision.marketplace.web.dtos.product.EditProductDto;
import eu.elision.marketplace.web.dtos.product.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

@RestController
public class ProductController
{
    final Controller controller;
    final UserService userService;
    static final String SUCCESS = "success";

    public ProductController(Controller controller, UserService userService) {
        this.controller = controller;
        this.userService = userService;
    }

    @PostMapping("/addProduct")
    ResponseEntity<ResponseDto> addProduct(@RequestBody ProductDto productDto) {
        controller.saveProduct(productDto);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
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

    @PostMapping("/addCategory")
    ResponseEntity<ResponseDto> addCategory(@RequestBody CategoryMakeDto categoryMakeDto) {
        controller.saveCategory(categoryMakeDto);
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

    @Secured("ROLE_VENDOR")
    @PostMapping("/editProduct")
    ResponseEntity<ResponseDto> editProduct(@RequestBody EditProductDto editProductDto, Principal principal) {
        controller.editProduct(editProductDto, principal.getName());
        return ResponseEntity.ok(new ResponseDto(SUCCESS));
    }

}
