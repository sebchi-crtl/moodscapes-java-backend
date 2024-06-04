package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.repository.AuthRepo;
import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEmailService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IMagicService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.moodscapes.backend.moodscapes.backend.enumeration.SignInMethod.MagicLink;

@Service
@RequiredArgsConstructor
@Slf4j
public class MagicService extends AuthService implements IMagicService{

    private final AuthRepo auth;
    private IUserService userService;
    private final IEmailService emailService;

    @Override
    public String token() {
        return token(64);
    }

    @Override
    public String issueToken(String email){
//        Todo: import this to auth service with exceptions and try catch
        var token = token();
        return token;
    }

    @Override
    public void sendAuthentication(String email) {
        try{

            String token = issueToken(email);
            Auth magic = saveToken(email, token);
            if (userService.getUserByEmail(email))
                emailService.sendMagicTokenMail(magic);

            else
                emailService.sendMagicTokenMailToNewUser(magic);

        }catch (Exception ex){

        }


    }
    @Override
    protected Auth saveToken(String email, String token) {
        Auth magic = auth.save(
                Auth
                        .builder()
                        .email(email)
                        .token(token)
                        .methodProvider(MagicLink)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return magic;
    }

}
