package com.moodscapes.backend.moodscapes.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@JsonInclude(NON_DEFAULT)
public record HttpResponse(
        LocalDateTime timestamp,
        int statusCode,
        String path,
        HttpStatus status,
        String message,
        String exception,
        String developerMessage,
        Map<?,?> data
) {
}
