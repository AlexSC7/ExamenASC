package com.exam.asc.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ApiClientConfig {

    @Bean
    public RestClient pedidosRestClient(@Value("${pedidos.url}") String url) {
        return RestClient.builder().baseUrl(url).build();
    }

    @Bean
    public RestClient itemsRestClient(@Value("${items.url}") String url) {
        return RestClient.builder().baseUrl(url).build();
    }
}