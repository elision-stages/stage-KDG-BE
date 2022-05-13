package eu.elision.marketplace.web.dtos.cart;

import java.util.Collection;

public record CartDto(Collection<OrderLineDto> orderLines, double totalPrice) {
}
