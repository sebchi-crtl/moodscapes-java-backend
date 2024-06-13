package com.moodscapes.backend.moodscapes.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodscapes.backend.moodscapes.backend.dto.request.AuthenticationRequest;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IJwtProvider;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;

import static com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.handleErrorRequest;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final IUserService userService;
    private final IJwtProvider jwtProvider;
    public JwtAuthenticationFilter(AuthenticationManager manager, IUserService userService, IJwtProvider jwtProvider) {
        super(new AntPathRequestMatcher("/api/v1/login"), manager);
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        //        setFilterProcessesUrl("/api/login", POST.name(), manager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {
        try {
            var authenticationRequest =
                    new ObjectMapper()
                            .configure(AUTO_CLOSE_SOURCE, true)
                            .readValue(
                                    request.getInputStream(),
                                    AuthenticationRequest.class
                            );
            var authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.username(),
                    authenticationRequest.password(),
                    new ArrayList<>()

            );
            return getAuthenticationManager().authenticate(authentication);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            handleErrorRequest(request, response, e);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authentication);
    }
}
