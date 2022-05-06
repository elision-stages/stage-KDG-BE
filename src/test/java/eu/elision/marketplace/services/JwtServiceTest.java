package eu.elision.marketplace.services;

import eu.elision.marketplace.domain.users.Customer;
import eu.elision.marketplace.domain.users.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JwtServiceTest {
    @Autowired
    private JwtService jwtService;

    @Test
    void testTokens()
    {
        User testUser = new Customer();
        testUser.setId(420420l);
        testUser.setEmail("test@test.com");
        testUser.setFirstName("Test user");
        testUser.setLastName("Test user");
        assertThat(jwtService.validateToken(jwtService.generateToken(testUser), testUser)).isTrue();
    }
}
