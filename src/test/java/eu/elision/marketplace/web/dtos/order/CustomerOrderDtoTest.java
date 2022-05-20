package eu.elision.marketplace.web.dtos.order;

import eu.elision.marketplace.web.dtos.cart.OrderLineDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerOrderDtoTest
{
    @Test
    void testDto()
    {
        final LocalDate now = LocalDate.now();
        final double totalPrice = RandomUtils.nextDouble();
        final ArrayList<OrderLineDto> lines = new ArrayList<>();
        final String customerName = RandomStringUtils.randomAlphabetic(5);
        final String customerMail = RandomStringUtils.randomAlphabetic(5);
        final long id = RandomUtils.nextLong();
        CustomerOrderDto customerOrderDto = new CustomerOrderDto(id, customerMail, customerName, lines, totalPrice, now);

        assertThat(customerOrderDto.orderDate).isEqualTo(now);
        assertThat(customerOrderDto.totalPrice).isEqualTo(totalPrice);
        assertThat(customerOrderDto.lines).isEqualTo(lines);
        assertThat(customerOrderDto.customerName).isEqualTo(customerName);
        assertThat(customerOrderDto.customerMail).isEqualTo(customerMail);
        assertThat(customerOrderDto.id).isEqualTo(id);
    }
}