package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.services.helpers.HelperMethods;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void getSetCart() {
        Customer customer = new Customer();

        Cart cart = new Cart();
        OrderLine ol = new OrderLine();
        ol.setOrderNumber(String.valueOf(HelperMethods.randomInt()));
        ol.setProduct(new Product());
        ol.setQuantity(HelperMethods.randomInt());
        ol.setVendor(new Vendor());
        ol.setOrderLineNumber(HelperMethods.randomInt());
        cart.getOrderLines().add(ol);
        customer.setCart(cart);

        assertThat(customer.getCart()).isEqualTo(cart);
    }

    @Test
    void getSetMainAddress() {
        Address address = new Address();
        address.setCity(HelperMethods.randomString(5));
        address.setNumber(HelperMethods.randomString(2));
        address.setPostalCode(HelperMethods.randomString(4));
        address.setStreet(HelperMethods.randomString(5));

        Customer customer = new Customer();
        customer.setMainAddress(address);

        assertThat(customer.getMainAddress()).isEqualTo(address);
    }

    @Test
    void getSetOtherAddresses() {
        Address address1 = new Address();
        address1.setCity(HelperMethods.randomString(5));
        address1.setNumber(HelperMethods.randomString(2));
        address1.setPostalCode(HelperMethods.randomString(4));
        address1.setStreet(HelperMethods.randomString(5));

        Address address2 = new Address();
        address2.setCity(HelperMethods.randomString(5));
        address2.setNumber(HelperMethods.randomString(2));
        address2.setPostalCode(HelperMethods.randomString(4));
        address2.setStreet(HelperMethods.randomString(5));

        Customer customer = new Customer();
        customer.getOtherAddresses().add(address1);
        customer.getOtherAddresses().add(address2);

        assertThat(customer.getOtherAddresses()).hasSize(2);
        assertThat(customer.getOtherAddresses().stream().anyMatch(a -> a.equals(address1))).isTrue();
        assertThat(customer.getOtherAddresses().stream().anyMatch(a -> a.equals(address2))).isTrue();
    }
}