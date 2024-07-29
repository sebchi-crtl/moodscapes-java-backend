package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.config.CustomUserDetailsService;
import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.entity.NewUserCheck;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.repository.AuthRepo;
import com.moodscapes.backend.moodscapes.backend.repository.NewUserCheckRepo;
import com.moodscapes.backend.moodscapes.backend.security.ApiAuthenticationProvider;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEmailService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IMagicService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import static com.moodscapes.backend.moodscapes.backend.enumeration.SignInMethod.MagicLink;
import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Slf4j
public class MagicService extends AuthService implements IMagicService{

    private final AuthRepo auth;
    private final IUserService userService;
    private final IEmailService emailService;
    private final NewUserCheckRepo newUserCheckRepo;
    private final CustomUserDetailsService userDetailsService;
    private final ApiAuthenticationProvider authenticationProvider;
    private final AuthenticationManager authenticationManager;

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
            String token = issueToken(email);
            log.info("This is the token {} "+token);
            Auth magic = saveToken(email, token);
            log.info("This is the auth Magic {} "+magic);
            if (userService.getUserByEmail(email))
                emailService.sendMagicTokenMail(magic);
            else emailService.sendMagicTokenMailToNewUser(magic);
        }catch (Exception ex){throw new ApiException("unable to send token");}


    }
    @Override
    protected Auth saveToken(String email, String token) {
        Auth magic = auth.save(
                Auth
                        .builder()
                        .email(email)
                        .token(token)
                        .methodProvider(MagicLink)
                        .createdAt(now())
                        .build()
        );
        return magic;
    }

    @Override
    @Transactional
    protected void authenticate(String token) {
        try {
            var confirmToken = getAuthConfirmToken(token);
            System.out.println("did the token come in here again? " + token);
            log.info("what is the confirm token saying? " + confirmToken);
            System.out.println("did it confirm to get email? " + confirmToken.getEmail());
            String email = confirmToken.getEmail();
            var user = userService.getaUserByEmail(email);
            var userM = userService.findUserByEmail(email);
            if (user.isPresent()) {
                String id = userM.getId();
                var credential = userService.getUserCredentialById(id);
                signingInUser(email, credential.getPassword());
            }
            log.info("checking if ");
            var existingCheck = newUserCheckRepo.findByEmail(email);
            if (existingCheck == null) {
                var newCheck = new NewUserCheck(true, email);
                log.info("User check: " + newCheck);
                newUserCheckRepo.save(newCheck);
            } else {
                log.info("User check already exists for email: " + email);
            }
            auth.deleteById(confirmToken.getId());
        }
        catch(Exception ex) {
            log.error("Error in authenticate method: " + ex.getMessage());
            throw new ApiException("Failed to authenticate: " + ex.getMessage());
        }
    }

    protected void signingInUser(String email, String password) {
        log.info("authenticating user: {}", email);
//        authenticationProvider.authenticate(authentication);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));
        log.info("User authenticated: {}", authentication.isAuthenticated());
//        log.info("signing in user");
//        var user =  userDetailsService.loadUserByUsername(email);
//        var username = user.getUsername();
//        log.info("This is the load userDetailsService {} "+username);

    }

    private Auth getAuthConfirmToken(String token) {
        log.info("This is the token " + token + " and receiving the error");
        return auth.findByToken(token).orElseThrow(() -> new ApiException("token not found"));
    }

}
