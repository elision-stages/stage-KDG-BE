package eu.elision.marketplace.web.config.filters;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class JwtFilterTest {
    @Autowired
    private JwtFilter jwtFilter;

    @Test
    void testNoCookies() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilterInternal(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testNullToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Cookie tokenCookie = new Cookie("jwt", null);
        request.setCookies(tokenCookie);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilterInternal(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testInvalidToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Cookie tokenCookie = new Cookie("jwt", "fakeToken");
        request.setCookies(tokenCookie);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilterInternal(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void testFakeToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        Cookie tokenCookie = new Cookie("jwt", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        request.setCookies(tokenCookie);
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();
        jwtFilter.doFilterInternal(request, response, filterChain);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}
