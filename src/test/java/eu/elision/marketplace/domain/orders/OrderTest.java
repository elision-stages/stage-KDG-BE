package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testEquals() {
        Order order1= new Order();
        Order order2 = new Order();

        User customer = new Customer();
        List<OrderLine> lines = new ArrayList<>();
        Address address = new Address();

        order1.setOrderNumber("1");
        order1.setUser(customer);
        order1.setLines(lines);
        order1.setShippingAddress(address);

        order2.setOrderNumber("1");
        order2.setUser(customer);
        order2.setLines(lines);
        order2.setShippingAddress(address);

        assertThat(order1.equals(order2)).isTrue();
    }

    @Test
    void testHashCode() {
        Order order = new Order();

        assertThat(order.hashCode()).isNotZero();
    }

    @Test
    void testToString() {
        Order order= new Order();

        order.setOrderNumber("1");

        assertThat(order.toString()).hasToString("Order(orderNumber=1, user=null, shippingAddress=null, lines=[])") ;
    }
}