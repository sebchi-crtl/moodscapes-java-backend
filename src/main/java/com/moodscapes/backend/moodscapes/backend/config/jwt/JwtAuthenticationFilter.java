package com.moodscapes.backend.moodscapes.backend.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.dto.request.AuthenticationRequest;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.enumeration.TokenType;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE;
import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.LOGIN_PATH;
import static com.moodscapes.backend.moodscapes.backend.enumeration.TokenType.*;
import static com.moodscapes.backend.moodscapes.backend.enumeration.TokenType.REFRESH;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.handleErrorRequest;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private IUserService userService;
    @Autowired
    private IJwtProvider jwtProvider;
    public JwtAuthenticationFilter(AuthenticationManager manager, IUserService userService, IJwtProvider jwtProvider) {
        super(new AntPathRequestMatcher(LOGIN_PATH), manager);
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
        var user = (UserPrincipal) authentication.getPrincipal();
//        userService.
        var httpResponse = sendResponse(request, response, user);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(OK.value());
        var out = response.getOutputStream();
        var mapper = new ObjectMapper();
        mapper.writeValue(out, httpResponse);
        out.flush();
    }
//    @Override
//    protected  void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
//
//    }

    private HttpResponse sendResponse(HttpServletRequest request, HttpServletResponse response, UserPrincipal user) {
        jwtProvider.addCookie(response, user, ACCESS);
        jwtProvider.addCookie(response, user, REFRESH);
        return  getResponse(request, Map.of("user", user), "Login Success", OK);
    }
}
