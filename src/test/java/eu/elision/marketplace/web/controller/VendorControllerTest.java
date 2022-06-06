package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.web.api.vat.Business;
import eu.elision.marketplace.web.dtos.TokenDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VendorControllerTest
{
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
    void testVatCheck() {
        ResponseEntity<Business> response = restTemplate.getForEntity(String.format("%s/vendor/vat/%s", base, "BE0458402105"), Business.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        /*assertThat(response.getBody().getName()).isEqualTo("VZW Karel de Grote Hogeschool, Katholieke Hogeschool Antwerpen");
        assertThat(response.getBody().getCountryCode()).isEqualTo("BE");
        assertThat(response.getBody().getVatNumber()).isEqualTo("0458402105");
        assertThat(response.getBody().getAddress()).isEqualTo("Brusselstraat 45\n2018 Antwerpen");*/
    }

    @Test
    void testRenewToken() {
        ResponseEntity<TokenDto> response = restTemplate.postForEntity(String.format("%s/vendor/renewToken", base), null, TokenDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

}