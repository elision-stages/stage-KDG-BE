package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.logic.Controller;
import eu.elision.marketplace.web.dtos.users.CustomerDto;
import eu.elision.marketplace.web.dtos.users.VendorDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan
class UserControllerTest {
    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;
    @MockBean
    private Controller controller;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

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

        final CustomerDto customerDto = new CustomerDto(firstName, lastName,
                email,
                password);

        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("%s/register/customer", base),
                customerDto,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("success");
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
        final String introduction = RandomStringUtils.randomAlphabetic(50);
        final String vatNumber = "BE0458402105";
        final String phoneNumber = RandomStringUtils.random(10, false, true);
        final String businessName = RandomStringUtils.randomAlphabetic(10);

        ResponseEntity<String> response = restTemplate.postForEntity(
                String.format("%s/register/vendor", base),
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
        assertThat(response.getBody()).contains("success");
    }
}