package eu.elision.marketplace.web.config.filters;

import eu.elision.marketplace.logic.services.users.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * Filter used by jwt
 */
@Component
public class JwtFilter extends OncePerRequestFilter
{

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    private static final Log log = LogFactory.getLog(JwtFilter.class);

    /**
     * Public constructor
     *
     * @param userDetailsService the user detail service that the jwt filter has to use
     * @param jwtService         the jwt service that the jwt filter has to use
     */
    public JwtFilter(UserDetailsService userDetailsService, JwtService jwtService)
    {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        if (request.getCookies() != null)
        {
            final Cookie tokenCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("jwt"))
                    .findFirst()
                    .orElse(null);
            final String jwtToken = tokenCookie == null ? "" : tokenCookie.getValue();

            String username = null;
            try
            {
                username = jwtService.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException | MalformedJwtException | SignatureException | ExpiredJwtException e)
            {
                log.info("JWT Token is invalid or has expired");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                try
                {
                    if (Boolean.TRUE.equals(jwtService.validateToken(jwtToken, userDetails)))
                    {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (NullPointerException e)
                {
                    // This occurs when a user has changed his username and uses his old jwt token to authenticate instead of the newly generated one.
                    log.info("Valid bearer token with invalid claims");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
