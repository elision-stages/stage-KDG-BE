package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class TestCart
{
    @Test
    void testConstructor(){
        Cart cart = new Cart();

        assertThat(cart.getOrderLines()).isNotNull();
    }

    @Test
    void testGetter(){
        var cart = new Cart();
        cart.getOrderLines().add(new OrderLine(1, new Vendor(), "1", new Product(), 1));

        assertThat(cart.getOrderLines()).hasSize(1);
    }

    @Test
    void cartTotalPrice()
    {
        Product product = new Product(2, new Vendor(), "product", new ArrayList<>(), new ArrayList<>());
        OrderLine ol = new OrderLine(1, new Vendor(), "1", product, 2);
        OrderLine ol2 = new OrderLine(1, new Vendor(), "1", product, 3);

        var cart = new Cart();

        cart.getOrderLines().add(ol);
        cart.getOrderLines().add(ol2);

        assertEquals("5 orderLines of price 2 should be 10", 10.0, cart.getTotalPrice());
        assertThat(cart.getTotalPrice()).isEqualTo(10.0);
    }
}
