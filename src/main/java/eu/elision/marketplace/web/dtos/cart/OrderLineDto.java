package eu.elision.marketplace.web.dtos.cart;

import eu.elision.marketplace.web.dtos.product.SmallProductDto;

import java.io.Serializable;

public record OrderLineDto(int quantity, SmallProductDto product) implements Serializable {
}
