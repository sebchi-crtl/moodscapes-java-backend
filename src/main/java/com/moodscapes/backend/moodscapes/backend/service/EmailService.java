package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.moodscapes.backend.moodscapes.backend.util.EmailUtils.getNewAccountEmailMessage;
import static com.moodscapes.backend.moodscapes.backend.util.EmailUtils.getTokenEmailMessage;

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
    @Value("${app.auth.magiclink.base-url}")
    private String baseUrl;


    @Override
    @Async
    public void sendMagicTokenMail(Auth token) {

        String magicLink = baseUrl + "/api/v1/auth/magic-link/{}" + token.getToken();
        try {
            log.info("{} has link {}", token.getEmail(), magicLink);
//            var mailSender1 = getMailSender(token.getEmail());
//            mailSender1.setSubject(MAGIC_LINK_EMAIL_VERFICATION);
//            mailSender1.setText(getTokenEmailMessage(token.getToken(), token.getEmail(), host));
//            mailSender.send(mailSender1);

        }
        catch (MailException e){
            log.error(e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to send email");
        }
    }

    public SimpleMailMessage getMailSender(String to) {
        var message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        return message;
    }

    @Override
    @Async
    public void sendMagicTokenMailToNewUser(Auth account) {
        String magicLink = baseUrl + "/api/v1/auth/magic-link/{}" + account.getToken();
        try {
            log.info("{} has link {}", account.getEmail(), magicLink);
//            var mailSender1 = getMailSender(account.getEmail());
//            mailSender1.setSubject(NEW_USER_ACCOUNT_WELCOME);
//            mailSender1.setText(getNewAccountEmailMessage(account.getToken(), account.getEmail(), host));
//            mailSender.send(mailSender1);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to send email");
        }
    }
}
