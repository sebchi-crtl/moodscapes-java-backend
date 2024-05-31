package com.moodscapes.backend.moodscapes.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message){super(message);}
}
