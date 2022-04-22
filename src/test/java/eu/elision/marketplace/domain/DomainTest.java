package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.Order;
import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.springframework.test.util.AssertionErrors.assertEquals;

class DomainTest
{
    @Test
    void orderLineTotalPrice()
    {
        var ol = new OrderLine(1, new Vendor(), "1",
                new Product(
                        2, new Vendor(), "product", new ArrayList<>(), new ArrayList<>()
                ),
                2);
        assertEquals("2 orderLines of price 2 should be 4", 4.0, ol.getTotalPrice());
    }

    @Test
    void cartTotalPrice()
    {
        var product = new Product(2, new Vendor(), "product", new ArrayList<>(), new ArrayList<>());
        var ol = new OrderLine(1, new Vendor(), "1", product, 2);
        var ol2 = new OrderLine(1, new Vendor(), "1", product, 3);

        var cart = new Cart();

        cart.getOrderLines().add(ol);
        cart.getOrderLines().add(ol2);

        assertEquals("5 orderLines of price 2 should be 10", 10.0, cart.getTotalPrice());
    }

    @Test
    void orderTotalPrice(){
        var product = new Product(2, new Vendor(), "product", new ArrayList<>(), new ArrayList<>());
        var ol = new OrderLine(1, new Vendor(), "1", product, 2);
        var ol2 = new OrderLine(1, new Vendor(), "1", product, 3);

        var order = new Order();

        order.getLines().add(ol);
        order.getLines().add(ol2);

        assertEquals("5 orderLines of price 2 should be 10", 10.0, order.getTotalPrice());
    }
}