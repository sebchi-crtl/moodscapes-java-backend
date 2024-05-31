package com.moodscapes.backend.moodscapes.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public record HttpResponse(
        String timestamp,
        int statusCode,
        HttpStatus status,
        String reason,
        String message,
        String developerMessage,
        Map<?,?> data
) {
}
