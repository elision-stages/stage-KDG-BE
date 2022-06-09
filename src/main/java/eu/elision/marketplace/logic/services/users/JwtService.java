package eu.elision.marketplace.logic.services.users;

import eu.elision.marketplace.domain.users.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for jwt tokens
 */
@Service
public class JwtService {
    @Value("${jwt.durationInMinutes}")
    private long jwtTokenValidity;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * Retrieve the e-mail address from a specific JWT token
     * @param token The token to retrieve the e-mail address from
     * @return The e-mail address as a string
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieve the expiration date from a specific JWT token
     * @param token The token to retrieve the expiration date from
     * @return The expiration date as a Date object
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Retrieves the specified claims from a specific token
     * @param token             Token to retrieve the claims from
     * @param claimsResolver    Functions to handle the retrieved claims
     * @param <T>               Types of the claims
     * @return The claim
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Geberate a JWT token for a specific user
     *
     * @param user User to generate a token for
     * @return The JWT token for the specified user as a string
     */
    public String generateToken(User user)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("firstname", user.getFirstName());
        claims.put("lastname", user.getLastName());
        return generateToken(claims, user.getEmail());
    }

    // TODO: 9/06/22 spelling 
    /**
     * Check if a specifik token is valid and matches specified UserDetails
     *
     * @param token       The token to validate
     * @param userDetails The UserDetails object to validate against
     * @return A boolean if the validation has succeeded
     */
    public Boolean validateToken(String token, UserDetails userDetails)
    {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String generateToken(Map<String, Object> claims, String subject)
    {
        Date expirationDate = new Date(System.currentTimeMillis() + jwtTokenValidity * 1000 * 60);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setIssuer("Marketplace")
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }
}
