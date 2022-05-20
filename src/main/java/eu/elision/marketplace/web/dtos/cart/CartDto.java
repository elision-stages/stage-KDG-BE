package eu.elision.marketplace.web.dtos.cart;

import java.util.Collection;

/**
 * Dto used for sending the cart info
 *
 * @param orderLines the orderlines of the cart
 * @param totalPrice the total price of the cart
 */
public record CartDto(Collection<OrderLineDto> orderLines, double totalPrice) {
}
