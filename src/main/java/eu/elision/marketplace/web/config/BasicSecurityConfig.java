package eu.elision.marketplace.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true
)
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter
{

    private static final String[] AUTH_WHITELIST = {
            "/hello",
            "/swagger-ui/"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        PasswordEncoder encoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN");
    }

    @Override
    public void configure(WebSecurity web)
    {
        web.ignoring().antMatchers(HttpMethod.POST, "/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest()
                .permitAll()
                .and()
                .httpBasic();
    }
}
