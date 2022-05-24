package eu.elision.marketplace.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for cors
 */
@Configuration
public class CorsConfiguration {
    @Value("${FE_URL}")
    String frontEndUrl;

    /**
     * Configure the cors policies
     *
     * @return the mvc configurer with the cors policies
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(frontEndUrl);
            }
        };
    }
}