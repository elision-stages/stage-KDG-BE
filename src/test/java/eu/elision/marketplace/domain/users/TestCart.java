package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TestCart {
    @Test
    void testConstructor() {
        Cart cart = new Cart();

        assertThat(cart.getOrderLines()).isNotNull();
    }

    @Test
    void testGetter() {
        var cart = new Cart();
        cart.getOrderLines().add(new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), new Product(), RandomUtils.nextInt()));

        assertThat(cart.getOrderLines()).hasSize(1);
    }

    @Test
    void cartTotalPrice() {
        final double price = Math.round(RandomUtils.nextDouble(1, 100));
        final int quantity1 = RandomUtils.nextInt(1, 10);
        final int quantity2 = RandomUtils.nextInt(1, 10);

        Product product = new Product(RandomUtils.nextLong(1,100), RandomStringUtils.random(5), price, new Category(), new Vendor(), RandomStringUtils.random(5), new ArrayList<>(), new ArrayList<>());
        OrderLine ol = new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), product, quantity1);
        OrderLine ol2 = new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), product, quantity2);

        var cart = new Cart();

        cart.getOrderLines().add(ol);
        cart.getOrderLines().add(ol2);

        assertThat(cart.getTotalPrice()).isEqualTo((quantity1 + quantity2) * price);
    }

    @Test
    void getSetId() {
        Cart cart = new Cart();
        long id = RandomUtils.nextLong();
        cart.setId(id);

        assertThat(cart.getId()).isEqualTo(id);
    }
}
