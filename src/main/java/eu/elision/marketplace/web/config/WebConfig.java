package eu.elision.marketplace.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Config of cors
 */
@Configuration
@EnableScheduling
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer
{

    /**
     * Add cors mappings
     *
     * @param registry the registery where the mapping needs to be added
     */
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        registry.addMapping("/**");
    }
}