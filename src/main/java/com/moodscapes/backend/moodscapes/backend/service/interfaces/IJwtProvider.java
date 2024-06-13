package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.domain.Token;
import com.moodscapes.backend.moodscapes.backend.domain.TokenData;
import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;
import java.util.function.Function;

public interface IJwtProvider {
    String generateToken(UserPrincipal auth, Function<Token, String> tokenFunction);
    Optional<String> extractToken(HttpServletRequest request, String token);
    void addCookie(HttpServletResponse response, User user, TokenType type);
    <T> T getTokenData(String token, Function<TokenData, String> tokenFunction);
    boolean isTokenValid(HttpServletRequest request);
}
