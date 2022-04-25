package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class TestOrder
{
    @Test
    void TestConstructor(){
        Order order = new Order();
        assertThat(order.getLines()).isNotNull();
    }

    @Test
    void testGetterSetter(){
        Order order = new Order();

        order.setOrderNumber("1");
        assertThat(order.getOrderNumber()).isEqualTo("1");

        User user = new Vendor();
        order.setUser(user);
        assertThat(order.getUser()).isEqualTo(user);

        Address address = new Address();
        order.setShippingAddress(address);
        assertThat(order.getShippingAddress()).isEqualTo(address);

        order.getLines().add(new OrderLine());
        assertThat(order.getLines()).hasSize(1);
    }
    @Test
    void orderTotalPrice()
    {
        var product = new Product(2, new Vendor(), "product", new ArrayList<>(), new ArrayList<>());
        var ol = new OrderLine(1, new Vendor(), "1", product, 2);
        var ol2 = new OrderLine(1, new Vendor(), "1", product, 3);

        var order = new Order();

        order.getLines().add(ol);
        order.getLines().add(ol2);

        assertEquals("5 orderLines of price 2 should be 10", 10.0, order.getTotalPrice());
        assertThat(order.getTotalPrice()).isEqualTo(10.0);
    }
}