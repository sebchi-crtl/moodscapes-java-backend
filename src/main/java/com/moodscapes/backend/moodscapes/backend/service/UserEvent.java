package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.entity.User;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Service;

public class UserEvent extends ApplicationEvent {
    private final User user;
    private boolean registration;

    public UserEvent( Object source,  User user, boolean registration) {
        super(user);
        this.user = user;
        this.registration = registration;
    }
}
