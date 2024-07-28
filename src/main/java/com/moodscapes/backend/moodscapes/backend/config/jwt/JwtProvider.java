package com.moodscapes.backend.moodscapes.backend.config.jwt;

import com.moodscapes.backend.moodscapes.backend.domain.Token;
import com.moodscapes.backend.moodscapes.backend.domain.TokenData;
import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.enumeration.TokenType;
import com.moodscapes.backend.moodscapes.backend.function.TriConsumer;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.*;
import static com.moodscapes.backend.moodscapes.backend.enumeration.TokenType.ACCESS;
import static com.moodscapes.backend.moodscapes.backend.enumeration.TokenType.REFRESH;
import static java.time.Instant.now;
import static java.util.Arrays.stream;
import static java.util.Date.from;
import static java.util.Optional.empty;
import static org.springframework.boot.web.server.Cookie.SameSite.NONE;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtProvider implements IJwtProvider{
    private final IUserService userService;
    private final JwtConfig jwtConfig;
    private final Supplier<SecretKey> key = () -> Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

    public String getSecretFromConfig() {
        return null; // Use the getter method from Lombok
    }
    private final Function<String, Claims> claimsFunction = token ->

            Jwts
                    .parser()
                    .verifyWith(key.get())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
    private final Function<String, String> subject = token ->
            getClaimsValue(token, Claims::getSubject);
    private final BiFunction<HttpServletRequest, String, Optional<String>> extractToken = (request, cookieName) ->
            Optional.of(stream(request.getCookies() == null ? new Cookie[]{
                    new Cookie(EMPTY_VALUE, EMPTY_VALUE)
            } : request.getCookies())
                    .filter(cookie -> Objects.equals(cookieName, cookie.getName()))
                    .map(Cookie::getValue)
                    .findAny())
                    .orElse(empty());
    private final BiFunction<HttpServletRequest, String, Optional<Cookie>> extractCookie = (request, cookieName) ->
            Optional.of(stream(request.getCookies() == null ? new Cookie[]{
                            new Cookie(EMPTY_VALUE, EMPTY_VALUE)
                    } : request.getCookies())
                            .filter(cookie -> Objects.equals(cookieName, cookie.getName()))
                            .findAny())
                            .orElse(empty());
    private final Supplier<JwtBuilder> builder = () ->
            Jwts.builder()
                    .header().add(Map.of(TYPE, JWT_TYPE))
                    .and()
                    .audience().add(GET_MOODSCAPES_LLC)
                    .and()
                    .id(UUID.randomUUID().toString())
                    .issuedAt(from(now()))
                    .notBefore(new Date())
                    .signWith(key.get(), Jwts.SIG.HS512);
    private final BiFunction<UserPrincipal, TokenType, String> tokenBuilder = (user, type) ->
            Objects.equals(type, ACCESS) ? builder.get()
                    .subject(user.getId())
                    .claim(AUTHORITIES, user.getAuthorities())
                    .claim(ROLE, user.getRole())
                    .expiration(from(now().plusMillis(jwtConfig.getExpiration_in_ms())))
                    .compact() : builder.get()
                    .subject(user.getId())
                    .expiration(from(now().plusMillis(jwtConfig.getExpiration_in_ms())))
                    .compact();
    private final TriConsumer<HttpServletResponse, UserPrincipal, TokenType> addCookie = (response, userPrincipal, type) -> {
        switch (type){
            case ACCESS -> {
                var accessToken = generateToken(userPrincipal, Token::getAccess);
                var cookie = new Cookie(type.getValue(), accessToken);
                cookie.setHttpOnly(true);
//                cookie.setSecure(true);
                cookie.setMaxAge(2 * 60);
                cookie.setPath("/");
                cookie.setAttribute("sameSite", NONE.name());
                response.addCookie(cookie);
            }
            case REFRESH -> {
                var refreshToken = generateToken(userPrincipal, Token::getRefresh);
                var cookie = new Cookie(type.getValue(), refreshToken);
                cookie.setHttpOnly(true);
//                cookie.setSecure(true);
                cookie.setMaxAge(2 * 60 * 60);
                cookie.setPath("/");
                cookie.setAttribute("sameSite", NONE.name());
                response.addCookie(cookie);
            }
        }
    };
    public Function<String, List<GrantedAuthority>> authorities = token ->
            commaSeparatedStringToAuthorityList(new StringJoiner(AUTHORITY_DELIMITER)
                    .add(claimsFunction
                            .apply(token)
                            .get(AUTHORITIES, String.class)
                    )
                    .add(ROLE_PREFIX + claimsFunction.apply(token).get(ROLE, String.class)).toString()
            );

    private <T> T getClaimsValue(String token, Function<Claims, T> claimsTFunction) {
        return claimsFunction.andThen(claimsTFunction).apply(token);
    }
    @Override
    public String generateToken(UserPrincipal auth, Function<Token, String> tokenFunction) {
        var token = Token.builder()
                .access(tokenBuilder
                        .apply(auth, ACCESS))
                .refresh(tokenBuilder
                        .apply(auth, REFRESH))
                .build();
        return tokenFunction.apply(token);
    }
    @Override
    public Optional<String> extractToken(HttpServletRequest request, String cookieName) {
        return extractToken.apply(request, cookieName);
    }
    @Override
    public void addCookie(HttpServletResponse response, UserPrincipal user, TokenType type) {
        addCookie.accept(response, user, type);
    }
    @Override
    public void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        var optionalCookie = extractCookie.apply(request, cookieName);
        if (optionalCookie.isPresent()){
            var cookie = optionalCookie.get();
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
    @Override
    public <T> T getTokenData(String token, Function<TokenData, T> tokenFunction) {
        return tokenFunction.apply(
                TokenData.builder()
                        .valid(Objects.equals(userService.getUserPrincipalByEmail(subject.apply(token)).getUserId(), claimsFunction.apply(token).getSubject()))
                        .authorities(authorities.apply(token))
                        .claims(claimsFunction.apply(token))
                        .user(userService.getUserPrincipalByEmail(subject.apply(token)))
                        .build()
        );
    }

}
