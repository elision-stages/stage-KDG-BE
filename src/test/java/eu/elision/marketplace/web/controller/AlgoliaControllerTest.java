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
public class AlgoliaControllerTest {
    private static URL base;
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() throws MalformedURLException
    {
        restTemplate = new TestRestTemplate();
        base = new URL(String.format("http://localhost:%s", port));
    }

    @Test
    void test() {
        ResponseEntity<String> response = restTemplate.postForEntity(String.format("%s/updatealgolia", base), null, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
