package com.moodscapes.backend.moodscapes.backend.config;

import lombok.RequiredArgsConstructor;
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

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorize) ->
                        authorize
                                .requestMatchers("/auth/**", "/oauth2/**", "/users/**")
                                .permitAll()
                                .requestMatchers("/super_admin/**").hasRole(ROLE_SUPAADMIN)
                                .requestMatchers("/admin/**").hasRole(ROLE_ADMIN)
                                .requestMatchers("/event/**").hasRole(ROLE_PLANNER)
                                .requestMatchers("/guest/**").hasRole(ROLE_PLANNER)
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
                );
//                .oauth2Login(oauth2Login ->
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
//                .addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
