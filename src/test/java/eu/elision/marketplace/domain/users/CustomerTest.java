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
}