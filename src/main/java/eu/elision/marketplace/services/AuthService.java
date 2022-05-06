package eu.elision.marketplace.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
@NoArgsConstructor
public class AuthService {
    @Value("${jwt.durationInMinutes}")
    private long jwtTokenValidity;
    @Value("${server.ssl.enabled}")
    private boolean ssl;

    public Cookie generateTokenCookie(String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setMaxAge((int) (jwtTokenValidity * 60));
        cookie.setHttpOnly(true);
        cookie.setSecure(ssl);
        cookie.setPath("/");
    }
}
