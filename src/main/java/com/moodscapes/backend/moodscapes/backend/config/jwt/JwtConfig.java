package com.moodscapes.backend.moodscapes.backend.config.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class JwtConfig {

    @Value("${app.jwt.expiration-in-ms}")
    private Long expiration_in_ms;
    @Value("${app.jwt.secret-key}")
    private String secret;
}
