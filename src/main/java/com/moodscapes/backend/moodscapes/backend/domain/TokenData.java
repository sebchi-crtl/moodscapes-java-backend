package com.moodscapes.backend.moodscapes.backend.domain;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@Builder
public class TokenData {
    private UserPrincipal user;
    private Claims claims;
    private boolean valid;
    private List<GrantedAuthority> authorities;
}
