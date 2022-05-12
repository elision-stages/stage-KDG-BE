package eu.elision.marketplace.web.dtos.cart;

import eu.elision.marketplace.web.dtos.ProductDto;

import java.io.Serializable;

public record OrderLineDto(int quantity, ProductDto productDto) implements Serializable {
}
