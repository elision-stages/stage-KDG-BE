package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class TestCart
{
    @Test
    void testConstructor()
    {
        Cart cart = new Cart();

        assertThat(cart.getOrderLines()).isNotNull();
    }

    @Test
    void testGetter()
    {
        var cart = new Cart();
        cart.getOrderLines().add(new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), new Product(), RandomUtils.nextInt()));

        assertThat(cart.getOrderLines()).hasSize(1);
    }

    @Test
    void cartTotalPrice()
    {
        final double price = Math.round(RandomUtils.nextDouble(1, 100));
        final int quantity1 = RandomUtils.nextInt(1, 10);
        final int quantity2 = RandomUtils.nextInt(1, 10);

        Product product = new Product(RandomUtils.nextLong(1, 100), RandomStringUtils.random(5), price, new Category(), new Vendor(), RandomStringUtils.random(5), new ArrayList<>(), new ArrayList<>());
        OrderLine ol = new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), product, quantity1);
        OrderLine ol2 = new OrderLine(RandomUtils.nextInt(), new Vendor(), String.valueOf(RandomUtils.nextInt()), product, quantity2);

        var cart = new Cart();

        cart.getOrderLines().add(ol);
        cart.getOrderLines().add(ol2);

        assertThat(cart.getTotalPrice()).isEqualTo((quantity1 + quantity2) * price);
    }

    @Test
    void getSetId()
    {
        Cart cart = new Cart();
        long id = RandomUtils.nextLong();
        cart.setId(id);

        assertThat(cart.getId()).isEqualTo(id);
    }

    @Test
    void addProductTest()
    {
        Cart cart = new Cart();
        Product product = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        final int quantity = RandomUtils.nextInt(1, 10);

        product.setId(1L);
        product.setPrice(price);

        cart.addProduct(product, quantity, false);
        assertThat(cart.getOrderLines()).hasSize(1);
        assertThat(cart.getTotalPrice()).isEqualTo(price * quantity);
    }

    @Test
    void add2ProductTest()
    {
        Cart cart = new Cart();
        Product product = new Product();
        Product product1 = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        final int price1 = RandomUtils.nextInt(1, 100);
        final int quantity = RandomUtils.nextInt(1, 10);
        final int quantity1 = RandomUtils.nextInt(1, 10);

        product.setId(1L);
        product.setPrice(price);

        product1.setId(2L);
        product1.setPrice(price1);

        cart.addProduct(product, quantity, false);
        cart.addProduct(product1, quantity1, false);

        assertThat(cart.getOrderLines()).hasSize(2);
        assertThat(cart.getTotalPrice()).isEqualTo((price * quantity) + (price1 * quantity1));
    }

    @Test
    void add2ProductAddTest()
    {
        Cart cart = new Cart();
        Product product = new Product();
        final int price = 2;
        final int quantity = 3;
        final int quantity1 = 2;

        product.setId(1L);
        product.setPrice(price);

        cart.addProduct(product, quantity, false);
        cart.addProduct(product, quantity1, true);

        assertThat(cart.getOrderLines()).hasSize(1);
        assertThat(cart.getTotalPrice()).isEqualTo((quantity + quantity1) * price);
    }

    @Test
    void add2ProductUpdateTest()
    {
        Cart cart = new Cart();
        Product product = new Product();

        final int price = RandomUtils.nextInt(1, 100);
        final int quantity = RandomUtils.nextInt(1, 10);
        final int quantity1 = RandomUtils.nextInt(1, 10);

        product.setId(1L);
        product.setPrice(price);

        cart.addProduct(product, quantity, false);
        cart.addProduct(product, quantity1, false);

        assertThat(cart.getOrderLines()).hasSize(1);
        assertThat(cart.getTotalPrice()).isEqualTo(quantity1 * price);
    }
}
