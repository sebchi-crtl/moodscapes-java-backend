package com.moodscapes.backend.moodscapes.backend.security;

import com.moodscapes.backend.moodscapes.backend.domain.ApiAuthentication;
import com.moodscapes.backend.moodscapes.backend.domain.UserAuthPrincipal;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class ApiAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final IUserService userService;
    private final BCryptPasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var apiAuthentication = authenticationFunction.apply(authentication);
        var user = userService.findUserPrincipalByEmail(apiAuthentication.getEmail());
        if (user != null){
            var userCredential = userService.getUserCredentialById(user.getId());
            var userPrincipal = new UserAuthPrincipal(user, userCredential);
            validAccount.accept(userPrincipal);
            if (encoder.matches(apiAuthentication.getPassword(), userCredential.getPassword()))
                return ApiAuthentication.authenticated(user, userPrincipal.getAuthorities());
            else throw new BadCredentialsException("unable to authenticate");
        }
        else
            throw new ApiException("Unable to authenticate");
    }
    private final Function<Authentication, ApiAuthentication> authenticationFunction = authentication -> (ApiAuthentication) authentication;
    @Override
    public boolean supports(Class<?> authentication) {
        return ApiAuthentication.class.isAssignableFrom(authentication);
    }
    private final Consumer<UserAuthPrincipal> validAccount = userAuthPrincipal -> {
        if (userAuthPrincipal.isAccountNonLocked()) throw new LockedException("your account is currently locked");
        if (userAuthPrincipal.isEnabled()) throw new DisabledException("your account is currently disabled");
        if (userAuthPrincipal.isCredentialsNonExpired()) throw new CredentialsExpiredException("your password has expired. Please change password");
        if (userAuthPrincipal.isAccountNonExpired()) throw new ApiException("your account has expired. Please contact admin");
    };
}
