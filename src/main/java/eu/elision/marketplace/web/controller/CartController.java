package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Rest controller for calls related to cart
 */
@RestController
@RequestMapping("cart")
public class CartController {
    private final Controller controller;

    /**
     * Public constructor
     *
     * @param controller the controller
     */
    public CartController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Rest call to add a product to a cart of a user
     *
     * @param principal     the principal the the logged in user
     * @param addProductDto the product dto with product id and quantity
     * @return a response entity with cart dto
     */
    @PostMapping("add")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<CartDto> addProductToCart(Principal principal, @RequestBody AddProductToCartDto addProductDto) {
        return ResponseEntity.ok(controller.addProductToCart(principal.getName(), addProductDto));
    }

    /**
     * Rest call to get a cart from a user
     *
     * @param principal the principal of the logged in user
     * @return a response entity with a cart dto of the given user
     */
    @GetMapping("/get")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<CartDto> getCart(Principal principal) {
        return ResponseEntity.ok(controller.getCustomerCart(principal.getName()));
    }

    /**
     * Rest call to checkout a cart from a user
     *
     * @param principal the principal of the logged in user
     * @return a response entity with the id of the order that is created
     */
    @GetMapping("/checkout")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<Long> checkoutCart(Principal principal) {
        return ResponseEntity.ok(controller.checkoutCart(principal.getName()));
    }
}
