package eu.elision.marketplace.web.dtos.cart;

import eu.elision.marketplace.web.dtos.product.SmallProductDto;

import java.io.Serializable;

/**
 * Dto used to add an order line to a cart
 *
 * @param quantity the quantity of a product
 * @param product  the product that needs to be added
 */
public record OrderLineDto(int quantity, SmallProductDto product) implements Serializable {
}
