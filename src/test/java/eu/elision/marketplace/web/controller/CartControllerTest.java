package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.product.Product;
import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.cart.AddProductToCartDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CartControllerTest
{
    @Autowired
    Controller controller;
    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() throws MalformedURLException
    {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL(String.format("http://localhost:%s", port));
    }

    @Test
    void testCheckout()
    {
        final Customer customer = new Customer();

        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPassword(password);

        controller.saveUser(customer);

        final Vendor vendor = new Vendor();
        vendor.setLastName(RandomStringUtils.randomAlphabetic(4));
        vendor.setFirstName(RandomStringUtils.randomAlphabetic(4));
        vendor.setPassword(password);
        vendor.setEmail(String.format("%s%s", email, RandomStringUtils.randomAlphabetic(3)));
        vendor.setPhoneNumber(RandomStringUtils.random(10, false, true));
        controller.saveUser(vendor);

        Product product = new Product();
        final int price = RandomUtils.nextInt(1, 100);
        product.setPrice(price);
        product.setVendor(vendor);


        final int count = RandomUtils.nextInt(1, 100);
        assertThat(
                controller.addProductToCart(email, new AddProductToCartDto(controller.saveProduct(product).getId(), count, false)).totalPrice())
                .isEqualTo(price * count);

        ResponseEntity<String> response = restTemplate.getForEntity(
                String.format("%s/cart/checkout", base),
                String.class
        );

        //assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}