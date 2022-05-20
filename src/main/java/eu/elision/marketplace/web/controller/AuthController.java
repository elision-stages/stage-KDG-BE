package eu.elision.marketplace.web.controller;

import eu.elision.marketplace.domain.users.User;
import eu.elision.marketplace.logic.helpers.Mapper;
import eu.elision.marketplace.logic.services.users.AuthService;
import eu.elision.marketplace.logic.services.users.JwtService;
import eu.elision.marketplace.logic.services.users.UserService;
import eu.elision.marketplace.web.dtos.AuthRequestDto;
import eu.elision.marketplace.web.dtos.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, UserService userService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("userinfo")
    @Secured("ROLE_CUSTOMER")
    public ResponseEntity<UserDetails> userinfo(Principal principal) {
        UserDetails user = userService.loadUserByUsername(principal.getName());

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid AuthRequestDto request, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.email(), request.password()
                            )
                    );

            User user = (User) authentication.getPrincipal();
            String token = jwtService.generateToken(user);
            Cookie cookie = authService.generateTokenCookie(token);
            response.addCookie(cookie);

            return new ResponseEntity<>(Mapper.toUserDto(user), HttpStatus.OK);
        } catch (BadCredentialsException | InternalAuthenticationServiceException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
