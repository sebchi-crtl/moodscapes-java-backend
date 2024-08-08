package com.moodscapes.backend.moodscapes.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@Slf4j
public class CorsConfig {

    @Value("#{'${cors.allowed-origins}'.split(',')}")
    public List<String> allowedOrigins;
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    public List<String> allowedMethods;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    public List<String> allowedHeaders;
    @Value("#{'${cors.exposed-headers}'.split(',')}")
    public List<String> expectedHeaders;
    public final long MAX_AGE_SECS = 3600L;


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(expectedHeaders);
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(MAX_AGE_SECS);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/v1/**", configuration);
        return source;
    }
}