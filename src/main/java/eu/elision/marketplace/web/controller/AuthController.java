package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.services.JwtService;
import eu.elision.marketplace.web.dtos.AuthRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("userinfo")
    public ResponseEntity<?> userinfo() {
        // TODO
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequestDto request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email(), request.password()
                            )
                    );

            User user = (User) authentication.getPrincipal();

            String token = jwtService.generateToken(user);

            Cookie cookie = new Cookie("jwt", token);

            // expires in 7 days
            cookie.setMaxAge(7 * 24 * 60 * 60);

            // optional properties
            //cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");

            // add cookie to response
            response.addCookie(cookie);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
