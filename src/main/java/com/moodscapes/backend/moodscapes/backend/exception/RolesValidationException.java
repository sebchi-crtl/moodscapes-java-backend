package com.moodscapes.backend.moodscapes.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RolesValidationException extends RuntimeException {
    public RolesValidationException(String message){super(message);}
}
