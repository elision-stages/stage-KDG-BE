package eu.elision.marketplace.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestControllerTest {
    private static final String BASE_URL = "http://localhost:";
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private Integer port;

    @Test
    void hello() {
        String response = testRestTemplate.getForObject(String.format("%s%s/hello", BASE_URL, port.toString()), String.class);
        assertThat(response).isEqualTo("hello");
    }

    @Test
    void testException() {
        ResponseEntity<String> respent = testRestTemplate.getForEntity(String.format("%s%s/testException", BASE_URL, port.toString()), String.class);
        assertThat(respent.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(respent.getBody()).isEqualTo("not found");
    }
}