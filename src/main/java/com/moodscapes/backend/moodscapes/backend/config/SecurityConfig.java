package com.moodscapes.backend.moodscapes.backend.config;

import com.moodscapes.backend.moodscapes.backend.config.jwt.IJwtProvider;
import com.moodscapes.backend.moodscapes.backend.config.jwt.JwtAuthenticationFilter;
import com.moodscapes.backend.moodscapes.backend.config.jwt.JwtAuthorizationFilter;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final IJwtProvider jwtProvider;
    private final IUserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(getStrength());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var authenticationFilter = new JwtAuthenticationFilter(authenticationManager(new AuthenticationConfiguration()), userService, jwtProvider);
        var authorizationFilter = new JwtAuthorizationFilter(authenticationManager(new AuthenticationConfiguration()), jwtProvider, userDetailsService);
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/auth/**", "/oauth2/**", "/users/**", "/guest/**")
                                .permitAll()
                                .requestMatchers("/super_admin/**").hasRole(ROLE_SUPAADMIN)
                                .requestMatchers("/admin/**").hasRole(ROLE_ADMIN)
                                .requestMatchers("/event/**").hasRole(ROLE_PLANNER)
                                .requestMatchers("/collections/**").hasAnyRole(ROLE_CENTER, ROLE_PLANNER, ROLE_ADMIN, ROLE_SUPAADMIN)
                                .requestMatchers("/booking/**").hasAnyRole(ROLE_CENTER, ROLE_VENDOR)
                                .requestMatchers("/center/**").hasRole(ROLE_CENTER)
                                .anyRequest()
                                .authenticated()
                )
                .logout(l -> l.logoutSuccessUrl("/").permitAll())
                .sessionManagement(
                        (session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(authenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//       .oauth2Login(oauth2Login ->
//                        oauth2Login
//                                .userInfoEndpoint(
//                                        userInfoEndpointConfig -> userInfoEndpointConfig
//                                                .userService(customOAuth2UserService)
//                                )
//                                .successHandler(customAuthenticationSuccessHandler)
//                )
//                .logout(l -> l.logoutSuccessUrl("/").permitAll())
//                .sessionManagement(
//                        (session) -> session
//                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//                .exceptionHandling(
//                        exceptionHandlingConfigurer -> exceptionHandlingConfigurer
//                                .authenticationEntryPoint(new HttpStatusEntryPoint(UNAUTHORIZED))
//                )
}
