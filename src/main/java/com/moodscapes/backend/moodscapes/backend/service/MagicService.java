package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.repository.AuthRepo;
import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEmailService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IMagicService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.moodscapes.backend.moodscapes.backend.enumeration.SignInMethod.MagicLink;

@Service
@RequiredArgsConstructor
@Slf4j
public class MagicService extends AuthService implements IMagicService{

    private final AuthRepo auth;
    private final IUserService userService;
    private final IEmailService emailService;
//    private SecurityContextHolder strategy;

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
            log.info("this is the String email {} " + email);
//            var user =  userDetailsService.loadUserByUsername(email);
            String token = issueToken(email);
            log.info("This is the token {} "+token);
//            var username = user.getUsername();
//            log.info("This is the load userDetailsService {} "+username);
            Auth magic = saveToken(email, token);
            log.info("This is the auth Magic {} "+magic);
            if (userService.getUserByEmail(email))
                emailService.sendMagicTokenMail(magic);
            else emailService.sendMagicTokenMailToNewUser(magic);
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

    @Override
    @Transactional
    protected void authenticate(String token, UserRequestDTO userRequestDTO) {
        try {
            var confirmToken = getAuthConfirmToken(token);
            System.out.println("did the token come in here again? " + token);
            log.info("did your user request reach here again? " + userRequestDTO);
            log.info("what is the confirm token saying? " + confirmToken);
            System.out.println("did it confirm to get email? " + confirmToken.getEmail());
//            var user = userService.findUserByEmail(confirmToken.getEmail());
//            log.info("did you find user email? " + user);
            if (userService.getUserByEmail(confirmToken.getEmail()) == false) {
                log.info("boolean find user {} " + userService.getUserByEmail(confirmToken.getEmail()));
                userService.createUser(userRequestDTO);
            } else {
                log.info("authenticating user {} " + userRequestDTO);
                signingInUser();
            }

            boolean enabled = true;
            log.info("is enabled set to true " + enabled);
            auth.deleteById(confirmToken.getId());
        }
        catch(Exception ex) {
            throw new ApiException(ex.getMessage());
        }
    }

    private void signingInUser() {
        log.info("signing in user");
    }

    private Auth getAuthConfirmToken(String token) {
        log.info("This is the token " + token + " and receiving the error");
        return auth.findByToken(token).orElseThrow(() -> new ApiException("token not found"));
    }

}
