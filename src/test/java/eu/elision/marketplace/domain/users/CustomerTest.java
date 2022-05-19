package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void getSetCart() {
        Customer customer = new Customer();

        Cart cart = new Cart();
        OrderLine ol = new OrderLine();
        ol.setOrderNumber(String.valueOf(RandomUtils.nextInt()));
        ol.setProduct(new Product());
        ol.setQuantity(RandomUtils.nextInt());
        ol.setOrderLineNumber(RandomUtils.nextInt());
        cart.getOrderLines().add(ol);
        customer.setCart(cart);

        assertThat(customer.getCart()).isEqualTo(cart);
    }

    @Test
    void getSetMainAddress() {
        Address address = new Address();
        address.setCity(RandomStringUtils.random(5));
        address.setNumber(RandomStringUtils.random(2));
        address.setPostalCode(RandomStringUtils.random(4));
        address.setStreet(RandomStringUtils.random(5));

        Customer customer = new Customer();
        customer.setMainAddress(address);

        assertThat(customer.getMainAddress()).isEqualTo(address);
    }

    @Test
    void getSetOtherAddresses() {
        Address address1 = new Address();
        address1.setCity(RandomStringUtils.random(5));
        address1.setNumber(RandomStringUtils.random(2));
        address1.setPostalCode(RandomStringUtils.random(4));
        address1.setStreet(RandomStringUtils.random(5));

        Address address2 = new Address();
        address2.setCity(RandomStringUtils.random(5));
        address2.setNumber(RandomStringUtils.random(2));
        address2.setPostalCode(RandomStringUtils.random(4));
        address2.setStreet(RandomStringUtils.random(5));

        Customer customer = new Customer();
        customer.getOtherAddresses().add(address1);
        customer.getOtherAddresses().add(address2);

        assertThat(customer.getOtherAddresses()).hasSize(2);
        assertThat(customer.getOtherAddresses().stream().anyMatch(a -> a.equals(address1))).isTrue();
        assertThat(customer.getOtherAddresses().stream().anyMatch(a -> a.equals(address2))).isTrue();
    }
}