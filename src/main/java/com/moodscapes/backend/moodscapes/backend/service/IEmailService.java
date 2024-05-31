package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import jakarta.validation.constraints.NotNull;

public interface IEmailService {

    void sendMagicTokenMail(@NotNull Auth token);
    void sendNewAccountMail(@NotNull Auth account);
}
