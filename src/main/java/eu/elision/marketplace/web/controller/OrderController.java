package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.web.dtos.order.OrderDto;
import eu.elision.marketplace.web.dtos.order.CustomerOrderDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

/**
 * Rest controller for managing calls about orders
 */
@RestController()
@RequestMapping("order")
public class OrderController {
    private final Controller controller;

    /**
     * Public constructor
     *
     * @param controller the controller that the rest controller has to use
     */
    public OrderController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Get all the orders of a vendor
     *
     * @param principal the principal of the vendor
     * @return a response entity with a collection of vendor dto orders
     */
    @GetMapping("myOrders")
    public ResponseEntity<Collection<OrderDto>> getVendorOrders(Principal principal) {
        return ResponseEntity.ok(controller.getOrders(principal.getName()));
    }

    /**
     *
     *
     */
    @GetMapping("{id}")
    @Secured({"ROLE_CUSTOMER", "ROLE_VENDOR", "ROLE_ADMIN"})
    public ResponseEntity<CustomerOrderDto> getOrder(Principal principal, @PathVariable long id) {
        return ResponseEntity.ok(controller.getOrder(principal.getName(), id));
    }
}
