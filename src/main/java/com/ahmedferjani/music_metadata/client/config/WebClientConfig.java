package com.ahmedferjani.music_metadata.client.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${GENIUS_API_BASE_URL}")
    private String baseUrl;

    @Value("${GENIUS_API_ACCESS_TOKEN}")
    private String accessToken;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {

        // set the base URL and the Bearer access token the webclient
        return webClientBuilder.baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + accessToken)
                .build();
    }
}
