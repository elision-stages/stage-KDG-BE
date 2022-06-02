package eu.elision.marketplace.logic.services.users;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

/**
 * Service for authentication
 */
@Service
@NoArgsConstructor
public class AuthService {
    @Value("${jwt.durationInMinutes}")
    private long jwtTokenValidity;
    @Value("${ssl}")
    private boolean ssl;

    /**
     * Create a Cookie of a specifik token
     * @param token String with the token to save as jwt cookie
     * @return The Cookie object
     */
    public ResponseCookie generateTokenCookie(String token) {
        return ResponseCookie
                .from("jwt", token)
                .httpOnly(true)
                .path("/")
                .maxAge((int) (jwtTokenValidity * 60))
                .sameSite("None")
                .secure(true)
                .build();
    }
}
