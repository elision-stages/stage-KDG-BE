package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.helpers.HelperMethods;
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

        final String orderNumber = String.valueOf(HelperMethods.randomInt());
        order.setOrderNumber(orderNumber);
        assertThat(order.getOrderNumber()).isEqualTo(orderNumber);

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
        final double price = HelperMethods.randomInt();
        final int quantity1 = HelperMethods.randomInt();
        final int quantity2 = HelperMethods.randomInt();

        var product = new Product(price, new Vendor(), HelperMethods.randomString(5), new ArrayList<>(), new ArrayList<>());
        var ol = new OrderLine(HelperMethods.randomInt(), new Vendor(), String.valueOf(HelperMethods.randomInt()), product, quantity1);
        var ol2 = new OrderLine(HelperMethods.randomInt(), new Vendor(), String.valueOf(HelperMethods.randomInt()), product, quantity2);

        var order = new Order();

        order.getLines().add(ol);
        order.getLines().add(ol2);

        assertThat(order.getTotalPrice()).isEqualTo((quantity1 + quantity2) * price);
    }
}