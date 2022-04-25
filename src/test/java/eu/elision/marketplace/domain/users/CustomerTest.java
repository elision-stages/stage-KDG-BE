package eu.elision.marketplace.domain.users;

import eu.elision.marketplace.domain.orders.OrderLine;
import eu.elision.marketplace.domain.product.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void getSetCart() {
        Customer customer = new Customer();

        Cart cart = new Cart();
        OrderLine ol = new OrderLine();
        ol.setOrderNumber("1");
        ol.setProduct(new Product());
        ol.setQuantity(2);
        ol.setVendor(new Vendor());
        ol.setOrderLineNumber(1);
        cart.getOrderLines().add(ol);
        customer.setCart(cart);

        assertThat(customer.getCart()).isEqualTo(cart);
    }

    @Test
    void getSetMainAddress() {
        Address address = new Address();
        address.setCity("Kontich");
        address.setNumber("35");
        address.setPostalCode("2850");
        address.setStreet("Veldkant");

        Customer customer = new Customer();
        customer.setMainAddress(address);

        assertThat(customer.getMainAddress()).isEqualTo(address);
    }

    @Test
    void getSetOtherAddresses() {
        Address address1 = new Address();
        address1.setCity("Kontich");
        address1.setNumber("35");
        address1.setPostalCode("2850");
        address1.setStreet("Veldkant");

        Address address2 = new Address();
        address2.setCity("Reet");
        address2.setNumber("23");
        address2.setPostalCode("2840");
        address2.setStreet("Pierstraat");

        Customer customer = new Customer();
        customer.getOtherAddresses().add(address1);
        customer.getOtherAddresses().add(address2);

        assertThat(customer.getOtherAddresses()).hasSize(2);
        assertThat(customer.getOtherAddresses().stream().anyMatch(a -> a.equals(address1))).isTrue();
        assertThat(customer.getOtherAddresses().stream().anyMatch(a -> a.equals(address2))).isTrue();
    }
}