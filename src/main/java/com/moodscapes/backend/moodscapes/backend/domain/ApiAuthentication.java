package com.moodscapes.backend.moodscapes.backend.domain;

import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

public class ApiAuthentication extends AbstractAuthenticationToken {
    private static final String PASSWORD_PROTECTED = "[PASSWORD_PROTECTED]";
    private static final String EMAIL_PROTECTED = "[EMAIL_PROTECTED]";
    private UserPrincipal user;
    private String email;
    private String password;
    private boolean authenticated;

    public ApiAuthentication(UserPrincipal user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
        this.password = PASSWORD_PROTECTED;
        this.email = EMAIL_PROTECTED;
        this.authenticated = true;
    }
    public ApiAuthentication(String email, String password) {
        super(NO_AUTHORITIES);
        this.email = email;
        this.password = password;
        this.authenticated = false;
    }
    public static ApiAuthentication unauthenticated(String email, String password) {
        return new ApiAuthentication(email, password);
    }
    public static ApiAuthentication authenticated(UserPrincipal user, Collection<? extends GrantedAuthority> authorities) {
        return new ApiAuthentication(user, authorities);
    }
    @Override
    public Object getCredentials() {
        return PASSWORD_PROTECTED;
    }
    @Override
    public Object getPrincipal() {
        return this.user;
    }
    @Override
    public void setAuthenticated(boolean authenticated) {
        throw new ApiException("you cannot set authentication");
    }
    @Override
    public boolean isAuthenticated() {
        return super.isAuthenticated();
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
}
