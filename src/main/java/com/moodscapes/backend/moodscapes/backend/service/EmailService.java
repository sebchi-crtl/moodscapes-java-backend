package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.moodscapes.backend.moodscapes.backend.utill.EmailUtils.getNewAccountEmailMessage;
import static com.moodscapes.backend.moodscapes.backend.utill.EmailUtils.getTokenEmailMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements IEmailService {

    private static final String NEW_USER_ACCOUNT_WELCOME = "New User Welcome Message";
    private static final String MAGIC_LINK_EMAIL_VERFICATION = "Magic Link Email Verification";
    private final JavaMailSender mailSender;
    @Value("${spring.mail.verify.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    @Async
    public void sendMagicTokenMail(Auth token) {
        log.info("{} has link http://localhost:8090/api/v1/auth/{}", token.getEmail(), token.getToken());
        try {
            var message = new SimpleMailMessage();
            message.setSubject(MAGIC_LINK_EMAIL_VERFICATION);
            message.setFrom(fromEmail);
            message.setTo(token.getEmail());
            message.setText(getTokenEmailMessage(token.getToken(), token.getEmail(), host));
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    @Async
    public void sendNewAccountMail(Auth account) {
        try {
            var message = new SimpleMailMessage();
            message.setSubject(NEW_USER_ACCOUNT_WELCOME);
            message.setFrom(fromEmail);
            message.setTo(account.getEmail());
            message.setText(getNewAccountEmailMessage(account.getToken(), account.getEmail(), host));
            mailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to send email");
        }
    }
}
