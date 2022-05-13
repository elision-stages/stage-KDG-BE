package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import eu.elision.marketplace.web.dtos.cart.CartDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("cart")
public class CartController {
    private final Controller controller;

    public CartController(Controller controller) {
        this.controller = controller;
    }

    @PostMapping("add")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<CartDto> addProductToCart(Principal principal, @RequestBody AddProductToCartDto addProductDto)
    {
        return ResponseEntity.ok(controller.addProductToCart(principal.getName(), addProductDto));
    }

    @GetMapping("/get")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<CartDto> getCart(Principal principal)
    {
        return ResponseEntity.ok(controller.getCustomerCart(principal.getName()));
    }

    @GetMapping("/checkout")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<Long> checkoutCart(Principal principal)
    {
        return ResponseEntity.ok(controller.checkoutCart(principal.getName()));
    }
}
