package eu.elision.marketplace.domain;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Cart;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.util.AssertionErrors.assertEquals;

class TestCart {
    @Test
    void testConstructor() {
        Cart cart = new Cart();

        assertThat(cart.getOrderLines()).isNotNull();
    }

    @Test
    void testGetter() {
        var cart = new Cart();
        cart.getOrderLines().add(new OrderLine(HelperMethods.randomInt(), new Vendor(), String.valueOf(HelperMethods.randomInt()), new Product(), HelperMethods.randomInt()));

        assertThat(cart.getOrderLines()).hasSize(1);
    }

    @Test
    void cartTotalPrice() {
        final double price = HelperMethods.randomInt();
        final int quantity1 = HelperMethods.randomInt();
        final int quantity2 = HelperMethods.randomInt();

        Product product = new Product(price, new Vendor(), HelperMethods.randomString(5), new ArrayList<>(), new ArrayList<>());
        OrderLine ol = new OrderLine(HelperMethods.randomInt(), new Vendor(), String.valueOf(HelperMethods.randomInt()), product, quantity1);
        OrderLine ol2 = new OrderLine(HelperMethods.randomInt(), new Vendor(), String.valueOf(HelperMethods.randomInt()), product, quantity2);

        var cart = new Cart();

        cart.getOrderLines().add(ol);
        cart.getOrderLines().add(ol2);

        assertThat(cart.getTotalPrice()).isEqualTo((quantity1 + quantity2) * price);
    }
}
