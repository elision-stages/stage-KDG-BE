package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import javax.print.attribute.standard.PrinterURI;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {

    @Test
    void getTotalPrice() {
        OrderLine orderLine = new OrderLine();

        double price = Math.random();
        int quantity = HelperMethods.randomInt();

        orderLine.setProduct(new Product(price, new Vendor(), HelperMethods.randomString(4), new ArrayList<>(), new ArrayList<>()));
        orderLine.setQuantity(quantity);

        assertThat(orderLine.getTotalPrice()).isEqualTo(price * quantity);
    }

    @Test
    void getOrderLineNumber() {
        OrderLine ol = new OrderLine();
        final int orderLineNumber = HelperMethods.randomInt();
        ol.setOrderLineNumber(orderLineNumber);

        assertThat(ol.getOrderLineNumber()).isEqualTo(orderLineNumber);
    }

    @Test
    void getVendor() {
        OrderLine ol = new OrderLine();
        Vendor vendor = new Vendor();
        ol.setVendor(vendor);

        assertThat(ol.getVendor()).isEqualTo(vendor);
    }

    @Test
    void getOrderNumber() {
        OrderLine ol = new OrderLine();
        final int orderLineNumber = HelperMethods.randomInt();

        ol.setOrderLineNumber(orderLineNumber);

        assertThat(ol.getOrderLineNumber()).isEqualTo(orderLineNumber);
    }

    @Test
    void getProduct() {
        OrderLine ol = new OrderLine();
        Product product = new Product();

        ol.setProduct(product);

        assertThat(ol.getProduct()).isEqualTo(product);
    }

    @Test
    void getQuantity() {
        OrderLine ol = new OrderLine();
        int quantity = HelperMethods.randomInt();

        ol.setQuantity(quantity);

        assertThat(ol.getQuantity()).isEqualTo(quantity);
    }

    @Test
    void testToString() {
        OrderLine ol = new OrderLine();
        final String orderNumber = String.valueOf(HelperMethods.randomInt(100));
        final String description = HelperMethods.randomString(10);
        final int orderLineNumber = HelperMethods.randomInt(100);
        final int quantity = HelperMethods.randomInt();
        final double price = HelperMethods.randomDouble();

        ol.setOrderNumber(orderNumber);
        ol.setQuantity(quantity);
        ol.setProduct(new Product(price, null, description, new ArrayList<>(), new ArrayList<>()));
        ol.setVendor(null);
        ol.setOrderLineNumber(orderLineNumber);

        assertThat(ol.toString()).hasToString(String.format("OrderLine(orderLineNumber=%s, vendor=null, orderNumber=%s, product=Product(price=%s, vendor=null, description=%s, images=[], attributes=[]), quantity=%s)", orderLineNumber, orderNumber, price, description, quantity));
    }

    @Test
    void testEquals() {
        Vendor vendor = new Vendor();
        Product product = new Product();

        final String orderNumber = String.valueOf(HelperMethods.randomInt(100));
        final int orderLineNumber = HelperMethods.randomInt(100);
        final int quantity = HelperMethods.randomInt(100);

        OrderLine ol1 = new OrderLine(orderLineNumber, vendor, orderNumber, product, quantity);
        OrderLine ol2 = new OrderLine(orderLineNumber, vendor, orderNumber, product, quantity);

        assertThat(ol1.equals(ol2)).isTrue();

        ol2.setOrderLineNumber(HelperMethods.randomInt(100));
        assertThat(ol1.equals(ol2)).isFalse();

        ol2.setOrderLineNumber(orderLineNumber);
        ol2.setQuantity(HelperMethods.randomInt(100));
        assertThat(ol1.equals(ol2)).isFalse();

        ol2.setQuantity(quantity);
        final Product product1 = new Product();
        product1.setDescription(HelperMethods.randomString(10));
        ol2.setProduct(product1);
        assertThat(ol1.equals(ol2)).isFalse();

        ol2.setProduct(product);
        final Vendor vendor1 = new Vendor();
        vendor1.setName(HelperMethods.randomString(10));
        ol2.setVendor(vendor1);
        assertThat(ol1.equals(ol2)).isFalse();

        assertThat(ol1.equals(ol1)).isTrue();
        assertThat(ol1.equals(new Vendor())).isFalse();
    }

    @Test
    void testHashCode() {
        OrderLine ol = new OrderLine();
        assertThat(ol.hashCode()).isNotZero();
    }
}