package com.moodscapes.backend.moodscapes.backend.config;

import com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.*;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@Slf4j
public class SecurityConfig {

//    private final CustomUserDetailsService userDetailsService;

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
