package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    private Customer testUser;
    private String token;

    @BeforeAll
    void init() {
        testUser = new Customer();
        testUser.setId(420420l);
        testUser.setEmail("test@test.com");
        testUser.setFirstName("Test user");
        testUser.setLastName("Test user");
        token = jwtService.generateToken(testUser);
    }

    @Test
    void testTokenValidationAndGeneration()
    {
        assertThat(jwtService.validateToken(token, testUser)).isTrue();
    }

    @Test
    void testCustomClaim() {
        assertThat(jwtService.getClaimFromToken(token, Claims::getSubject)).isEqualTo(testUser.getUsername());
    }

    @Test
    void testExpiration() {
        assertThat(jwtService.getExpirationDateFromToken(token)).isAfter(Instant.from(LocalDateTime.now()));
    }

    @Test
    void testUsernameClaim() {
        assertThat(jwtService.getUsernameFromToken(token)).isEqualTo(testUser.getUsername());
    }
}
