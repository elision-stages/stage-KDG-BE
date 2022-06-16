package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.product.category.Category;
import eu.elision.marketplace.domain.users.Vendor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderLineTest
{

    @Test
    void getTotalPrice()
    {
        OrderLine orderLine = new OrderLine();

        double price = Math.random();
        int quantity = RandomUtils.nextInt();

        orderLine.setProduct(new Product(RandomUtils.nextLong(1, 100), price, RandomStringUtils.random(4), new Category(), new Vendor(), RandomStringUtils.random(4), new ArrayList<>(), new ArrayList<>()));
        orderLine.setQuantity(quantity);

        assertThat(orderLine.getTotalPrice()).isEqualTo(price * quantity);
    }

    @Test
    void getOrderLineNumber()
    {
        OrderLine ol = new OrderLine();
        final int orderLineNumber = RandomUtils.nextInt();
        ol.setOrderLineNumber(orderLineNumber);

        assertThat(ol.getOrderLineNumber()).isEqualTo(orderLineNumber);
    }

    @Test
    void getOrderNumber()
    {
        OrderLine ol = new OrderLine();
        final int orderLineNumber = RandomUtils.nextInt();

        ol.setOrderLineNumber(orderLineNumber);

        assertThat(ol.getOrderLineNumber()).isEqualTo(orderLineNumber);
    }

    @Test
    void getProduct()
    {
        OrderLine ol = new OrderLine();
        Product product = new Product();

        ol.setProduct(product);

        assertThat(ol.getProduct()).isEqualTo(product);
    }

    @Test
    void getQuantity()
    {
        OrderLine ol = new OrderLine();
        int quantity = RandomUtils.nextInt();

        ol.setQuantity(quantity);

        assertThat(ol.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void testHashCode()
    {
        OrderLine ol = new OrderLine();
        assertThat(ol.hashCode()).isNotZero();
    }
}