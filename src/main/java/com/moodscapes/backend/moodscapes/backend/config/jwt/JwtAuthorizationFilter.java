package com.moodscapes.backend.moodscapes.backend.config.jwt;

import com.moodscapes.backend.moodscapes.backend.config.CustomUserDetailsService;
import com.moodscapes.backend.moodscapes.backend.enumeration.TokenType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final IJwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    public JwtAuthorizationFilter(AuthenticationManager manager, IJwtProvider jwtProvider, CustomUserDetailsService userDetailsService) {
        super(manager);
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        var accessToken = jwtProvider.extractToken(request, TokenType.ACCESS.getValue());
        if (accessToken.isPresent() && StringUtils.hasText(accessToken.get())) {
            jwtProvider.getTokenData(accessToken.get(), tokenData -> {
                if (tokenData.isValid()) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            tokenData.getUser(), null, tokenData.getAuthorities()
                    );
                    getContext().setAuthentication(auth);
                    return true;
                }
                return false;
            });
        }
        chain.doFilter(request, response);
    }
}
