package com.moodscapes.backend.moodscapes.backend.domain;

import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;

@RequiredArgsConstructor
@Data
@Builder
public final class UserAuthPrincipal implements UserDetails {
    @Serial
    private static final long serialVersionUID = 0L;
    private final UserPrincipal user;
    private final Credential credential;

//    public UserAuthPrincipal(UserPrincipal user, Credential credential) {
//        this.user = user;
//        this.credential = credential;
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Role.valueOf(user.getRole().toString()).getAuthorities();
    }

    @Override
    public String getPassword() {
        return credential.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    public UserPrincipal user() {
        return user;
    }

    public Credential credential() {
        return credential;
    }

}
