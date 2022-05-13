package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Address;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

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

        final long orderNumber = RandomUtils.nextLong();
        order.setOrderNumber(orderNumber);
        assertThat(order.getOrderNumber()).isEqualTo(orderNumber);

        User user = new Vendor();
        order.setUser(user);
        assertThat(order.getUser()).isEqualTo(user);

        Address address = new Address();
        //order.setShippingAddress(address);
        //assertThat(order.getShippingAddress()).isEqualTo(address);

        order.getLines().add(new OrderLine());
        assertThat(order.getLines()).hasSize(1);
    }
    @Test
    void orderTotalPrice()
    {
        final double price = Math.round(RandomUtils.nextDouble(1, 100));
        final int quantity1 = RandomUtils.nextInt(1, 10);
        final int quantity2 = RandomUtils.nextInt(1, 10);

        var product = new Product(RandomUtils.nextLong(1,100), price, new Vendor(), RandomStringUtils.random(4), new ArrayList<>(), new ArrayList<>());
        var ol = new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), product, quantity1);
        var ol2 = new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), product, quantity2);

        var order = new Order();

        order.getLines().add(ol);
        order.getLines().add(ol2);

        assertThat(order.getTotalPrice()).isEqualTo((quantity1 + quantity2) * price);
    }
}