package eu.elision.marketplace.web.dtos.order;

import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public final class CustomerOrderDto {
    long id;
    String customerMail;
    String customerName;
    List<OrderLineDto> lines;
    double totalPrice;
    LocalDate orderDate;
}
