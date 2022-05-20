package eu.elision.marketplace.web.controller;

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
class TestControllerTest {
    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("user", "password");
        base = new URL(String.format("http://localhost:%s", port));
    }

    @Test
    void hello() {
        String response = restTemplate.getForObject(String.format("%s/hello", base), String.class);
        assertThat(response).isEqualTo("hello");
    }

    @Test
    void testException() {
        ResponseEntity<String> respent = restTemplate.getForEntity(String.format("%s/testException", base), String.class);
        assertThat(respent.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(respent.getBody()).isEqualTo("{\"status\":\"not found\"}");
    }
}