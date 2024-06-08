package com.moodscapes.backend.moodscapes.backend.service;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
    private UserDetailsService userDetailsService;
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
            var user =  userDetailsService.loadUserByUsername(email);
            String token = issueToken(email);
            var username = user.getUsername();
            Auth magic = saveToken(username, token);
            if (userService.getUserByEmail(username))
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
    protected void authenticate(String token, HttpServletRequest request, HttpServletResponse response) {
        Auth entity = auth.findByToken(token);

        if (entity != null){
            var user = userDetailsService.loadUserByUsername(entity.getEmail());
            var userDetails = new User(
                    user.getPassword(),
                    user.getUsername(),
                    user.getAuthorities()
            );

            var authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    user.getPassword(),
                    user.getAuthorities()
            );
            SecurityContextHolderStrategy strategy = SecurityContextHolder.getContextHolderStrategy();
            SecurityContext context = strategy.createEmptyContext();
            context.setAuthentication(authentication);
            strategy.setContext(context);
            HttpSessionSecurityContextRepository repository = new HttpSessionSecurityContextRepository();
            repository.saveContext(context, request, response);
            auth.deleteById(entity.getId());

        }
    }

}
