package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.domain.users.Vendor;
import eu.elision.marketplace.services.Controller;
import eu.elision.marketplace.web.dtos.CustomerDto;
import eu.elision.marketplace.web.dtos.VendorDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;
    @Autowired
    private Controller controller;

    @BeforeEach
    void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL(String.format("http://localhost:%s", port));
    }

    @Test
    void testAddCustomer() {
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));

        final String street = RandomStringUtils.randomAlphabetic(4);
        final String number = String.valueOf(RandomUtils.nextInt(1, 100));
        final String postalCode = String.valueOf(RandomUtils.nextInt(1, 9999));
        final String city = RandomStringUtils.randomAlphabetic(10);

        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("%s/registercustomer", base),
                new CustomerDto(firstName, lastName,
                        email,
                        password),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        User customer = controller.findUserByEmailAndPassword(email, password);

        assertThat(customer.getFirstName()).hasToString(firstName);
        assertThat(customer.getLastName()).hasToString(lastName);
        assertThat(customer.getEmail()).hasToString(email);
        assertThat(customer.getPassword()).hasToString(password);
        assertThat(customer.isValidated()).isFalse();
        assertThat(customer).isInstanceOf(Customer.class);
    }

    @Test
    void testAddVendor() {
        final String firstName = RandomStringUtils.randomAlphabetic(4);
        final String lastName = RandomStringUtils.randomAlphabetic(4);
        final String email = String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(2));
        final String password = String.format("%s%s%s", RandomStringUtils.randomAlphabetic(5).toLowerCase(Locale.ROOT), RandomUtils.nextInt(1, 100), RandomStringUtils.randomAlphabetic(2).toUpperCase(Locale.ROOT));
        final boolean validated = RandomUtils.nextBoolean();
        final String logo = RandomStringUtils.randomAlphabetic(4);
        final String theme = RandomStringUtils.randomAlphabetic(4);
        final String introduction = RandomStringUtils.randomAlphabetic(4);
        final String vatNumber = RandomStringUtils.randomAlphabetic(4);
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        final String businessName = RandomStringUtils.randomAlphabetic(10);

        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("%s/registervendor", base),
                new VendorDto(
                        firstName, lastName,
                        email,
                        password,
                        validated,
                        logo,
                        theme,
                        introduction,
                        vatNumber,
                        phoneNumber,
                        businessName
                ),
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Vendor is valid");

        User vendor = controller.findUserByEmailAndPassword(email, password);

        assertThat(vendor).isInstanceOf(Vendor.class);
        assertThat(vendor.getFirstName()).hasToString(firstName);
        assertThat(vendor.getLastName()).hasToString(lastName);
        assertThat(vendor.getEmail()).hasToString(email);
        assertThat(vendor.getPassword()).hasToString(password);
        assertThat(vendor.isValidated()).isEqualTo(validated);
        assertThat(((Vendor) vendor).getLogo()).isEqualTo(logo);
        assertThat(((Vendor) vendor).getTheme()).isEqualTo(theme);
        assertThat(((Vendor) vendor).getIntroduction()).isEqualTo(introduction);
        assertThat(((Vendor) vendor).getVatNumber()).isEqualTo(vatNumber);
        assertThat(((Vendor) vendor).getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(((Vendor) vendor).getBusinessName()).isEqualTo(businessName);
    }

    @Test
    void testFindAll() {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("%s/allUsers", base), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void testUserNotValidated() {
        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("%s/registervendor", base), new Vendor(), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}