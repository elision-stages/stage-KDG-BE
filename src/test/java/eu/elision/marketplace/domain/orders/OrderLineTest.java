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
    void testToString()
    {
        OrderLine ol = new OrderLine();
        final String orderNumber = String.valueOf(RandomUtils.nextInt(1, 100));
        final String name = RandomStringUtils.random(10);
        final String description = RandomStringUtils.random(10);
        final int orderLineNumber = RandomUtils.nextInt(1, 100);
        final int quantity = RandomUtils.nextInt(1, 100);
        final double price = RandomUtils.nextDouble(1, 100);
        final long id = RandomUtils.nextLong(1, 100);

        ol.setOrderNumber(orderNumber);
        ol.setQuantity(quantity);
        ol.setProduct(new Product(id, price, name, null, null, description, new ArrayList<>(), new ArrayList<>()));
        ol.setOrderLineNumber(orderLineNumber);

        assertThat(ol.toString()).hasToString(String.format("OrderLine(orderLineNumber=%s, orderNumber=%s, product=Product(id=%s, price=%s, title=%s, category=null, vendor=null, description=%s, images=[], attributes=[]), quantity=%s)",
                orderLineNumber, orderNumber, id, price, name, description, quantity));
    }

    @Test
    void testEquals()
    {
        Product product = new Product();

        final String orderNumber = String.valueOf(RandomUtils.nextInt(1, 100));
        final int orderLineNumber = RandomUtils.nextInt(1, 100);
        final int quantity = RandomUtils.nextInt(1, 100);

        OrderLine ol1 = new OrderLine(orderLineNumber, orderNumber, product, quantity);
        OrderLine ol2 = new OrderLine(orderLineNumber, orderNumber, product, quantity);

        assertThat(ol1.equals(ol2)).isTrue();

        ol2.setOrderLineNumber(RandomUtils.nextInt(1, 100));
        assertThat(ol1.equals(ol2)).isFalse();

        ol2.setOrderLineNumber(orderLineNumber);
        ol2.setQuantity(RandomUtils.nextInt(1, 100));
        assertThat(ol1.equals(ol2)).isFalse();

        ol2.setQuantity(quantity);
        final Product product1 = new Product();
        product1.setDescription(RandomStringUtils.random(10));
        ol2.setProduct(product1);
        assertThat(ol1.equals(ol2)).isFalse();

        ol2.setProduct(product);
        final Vendor vendor1 = new Vendor();
        vendor1.setFirstName(RandomStringUtils.random(10));
        vendor1.setLastName(RandomStringUtils.random(10));
        assertThat(ol1.equals(ol2)).isTrue();
    }

    @Test
    void testHashCode()
    {
        OrderLine ol = new OrderLine();
        assertThat(ol.hashCode()).isNotZero();
    }
}