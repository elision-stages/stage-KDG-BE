package eu.elision.marketplace.web.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public WebClient getWebClient(final WebClient.Builder builder) {
        return builder.baseUrl("http://localhost").build();
    }


}
