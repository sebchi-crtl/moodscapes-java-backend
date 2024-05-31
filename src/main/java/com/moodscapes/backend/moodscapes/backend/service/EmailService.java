package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEmailService;

public class EmailService implements IEmailService {
    @Override
    public void sendMagicTokenMail(Auth token) {

    }

    @Override
    public void sendNewAccountMail(Auth account) {

    }
}
