package com.moodscapes.backend.moodscapes.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;

import javax.security.auth.login.CredentialExpiredException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

import static java.time.LocalDateTime.now;
import static java.util.Collections.emptyMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class RequestUtils {

    private static final BiConsumer<HttpServletResponse, HttpResponse> writeResponse = ((httpServletResponse, response) -> {
        try {
            var outputStream = httpServletResponse.getOutputStream();
            new ObjectMapper().writeValue(outputStream, response);
        }
        catch (Exception e){
            throw new ApiException(e.getMessage());
        }
    });

    private static final BiFunction<Exception, HttpStatus, String> errorReason = (exception, httpStatus) -> {
        if(httpStatus.isSameCodeAs(FORBIDDEN)) return "you dont have enough permission";
        if(httpStatus.isSameCodeAs(UNAUTHORIZED)) return "you are not login in yet";
        if (exception instanceof RuntimeException || exception instanceof LockedException || exception instanceof BadCredentialsException
        || exception instanceof CredentialExpiredException || exception instanceof ApiException)
            return exception.getMessage();
        if (httpStatus.is5xxServerError()) return "Internal Server error";
        else return "An error occurred, Please try again";
    };

    public static HttpResponse getResponse(HttpServletRequest request, Map<?, ?> data, String message, HttpStatus status){
        return new HttpResponse(LocalDateTime.now(), status.value(), request.getRequestURI(), HttpStatus.valueOf(status.value()), message, EMPTY, EMPTY, data);
    }

    public static void handleErrorRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            Exception exception)
    {
        if (exception instanceof AccessDeniedException){
            var apiResponse = getErrorRespose(request, response, exception, FORBIDDEN);
            writeResponse.accept(response, apiResponse);
        }
    }

    private static HttpResponse getErrorRespose(HttpServletRequest request, HttpServletResponse response, Exception exception, HttpStatus status) {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(status.value());
        return new HttpResponse(now(), status.value(), request.getRequestURI(), HttpStatus.valueOf(status.value()), errorReason.apply(exception, status), getRootCauseMessage(exception), "", emptyMap());
    }
}
