package eu.elision.marketplace.logic;

import eu.elision.marketplace.logic.services.users.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.Cookie;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthServiceTest {
    @Autowired
    private AuthService authService;

    @Test
    void testTokens()
    {
        Cookie cookie = authService.generateTokenCookie("randomToken");
        assertThat(cookie.getName()).isEqualTo("jwt");
        assertThat(cookie.getValue()).isEqualTo("randomToken");
    }
}
