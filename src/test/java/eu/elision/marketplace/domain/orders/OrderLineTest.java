package eu.elision.marketplace.domain.orders;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Vendor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderLineTest {

    @Test
    void getTotalPrice() {
        OrderLine orderLine = new OrderLine();
        orderLine.setProduct(new Product(2, new Vendor(), "desc", new ArrayList<>(), new ArrayList<>()));
        orderLine.setQuantity(2);

        assertThat(orderLine.getTotalPrice()).isEqualTo(4);
    }

    @Test
    void getOrderLineNumber() {
        OrderLine ol = new OrderLine();
        ol.setOrderLineNumber(1);

        assertThat(ol.getOrderLineNumber()).isEqualTo(1);
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
        ol.setOrderLineNumber(1);

        assertThat(ol.getOrderLineNumber()).isEqualTo(1);
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
        ol.setQuantity(1);

        assertThat(ol.getQuantity()).isEqualTo(1);
    }

    @Test
    void testToString() {
        OrderLine ol = new OrderLine();
        ol.setOrderNumber("1");
        ol.setQuantity(2);
        ol.setProduct(new Product(1, null, "desc", new ArrayList<>(), new ArrayList<>()));
        ol.setVendor(null);
        ol.setOrderLineNumber(1);

        assertThat(ol.toString()).hasToString("OrderLine(orderLineNumber=1, vendor=null, orderNumber=1, product=Product(price=1.0, vendor=null, description=desc, images=[], attributes=[]), quantity=2)");
    }
    @Test
    void testEquals(){
        Vendor vendor = new Vendor();
        Product product = new Product();
        OrderLine ol1 = new OrderLine(1, vendor, "1", product, 1);
        OrderLine ol2 = new OrderLine(1, vendor, "1", product, 1);

        assertThat(ol1.equals(ol2)).isTrue();
    }
}