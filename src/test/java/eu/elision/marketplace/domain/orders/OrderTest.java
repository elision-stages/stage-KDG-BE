package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderTest {

    @Test
    void testEquals() {
        Order order1 = new Order();
        Order order2 = new Order();

        Customer customer = new Customer();
        List<OrderLine> lines = new ArrayList<>();

        final long orderNumber = RandomUtils.nextLong(1, 100);

        order1.setOrderNumber(orderNumber);
        order1.setUser(customer);
        order1.setLines(lines);

        order2.setOrderNumber(orderNumber);
        order2.setUser(customer);
        order2.setLines(lines);

        assertThat(order1.equals(order2)).isTrue();

        order2.setOrderNumber(RandomUtils.nextLong(1, 100));
        assertThat(order1.equals(order2)).isFalse();
        order2.setOrderNumber(orderNumber);
        assertThat(order1.equals(order2)).isTrue();

        order2.setLines(new ArrayList<>(List.of(new OrderLine())));
        assertThat(order1.equals(order2)).isFalse();
        order2.setLines(lines);
        assertThat(order1.equals(order2)).isTrue();

        Customer customer1 = new Customer();
        customer1.setFirstName(RandomStringUtils.random(4));
        customer1.setLastName(RandomStringUtils.random(4));
        order2.setUser(customer1);
        assertThat(order1.equals(order2)).isFalse();
        order2.setUser(customer);
        assertThat(order1.equals(order2)).isTrue();

        assertThat(order1.equals(order1)).isTrue();
        assertThat(order1.equals(new Vendor())).isFalse();

    }

    @Test
    void testHashCode() {
        Order order = new Order();
        assertThat(order.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        Order order = new Order();

        final long orderNumber = RandomUtils.nextInt();
        order.setOrderNumber(orderNumber);

        assertThat(order.toString()).hasToString(String.format("Order(orderNumber=%s, user=null, lines=[], createdDate=%s)", orderNumber, LocalDate.now()));
    }
}