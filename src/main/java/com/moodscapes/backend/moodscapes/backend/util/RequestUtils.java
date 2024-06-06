package com.moodscapes.backend.moodscapes.backend.util;

import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static org.apache.commons.lang3.StringUtils.EMPTY;
public class RequestUtils {

    public static HttpResponse getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status){
        return new HttpResponse(LocalDateTime.now(), status.value(), request.getRequestURI(), HttpStatus.valueOf(status.value()), message, EMPTY, EMPTY, data);
    }
}
