package com.moodscapes.backend.moodscapes.backend.domain;

import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import com.moodscapes.backend.moodscapes.backend.util.UserUtils;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Data
@Builder
public class UserAuthPrincipal implements UserDetails {
    private final UserPrincipal user;
    private final Credential credential;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Role.valueOf(user.getRole().toString()).getAuthorities();
    }

    @Override
    public String getPassword() { return credential.getPassword(); }
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
}
