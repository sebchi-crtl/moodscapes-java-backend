package com.moodscapes.backend.moodscapes.backend.config;

import com.moodscapes.backend.moodscapes.backend.domain.UserAuthPrincipal;
import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.security.SecurityUtils;
import com.moodscapes.backend.moodscapes.backend.service.UserService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import com.moodscapes.backend.moodscapes.backend.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

//@Service
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("check for user email " + email);

        var user = userService
                .getaUserByEmail(email)
                .orElseThrow(
                        () ->
                                new UsernameNotFoundException("User not found with username: " + email)
                );
        List<GrantedAuthority> authorities = Collections.
                singletonList(SecurityUtils.convertToAuthority(user.getRole().toString()));

        Credential credential = new Credential();
        UserPrincipal userPrincipal = new UserPrincipal();
//        return new UserAuthPrincipal(userPrincipal, credential);
        return mapUserToUserPrincipal(userPrincipal, credential);
//        return null;
    }

    private UserAuthPrincipal mapUserToUserPrincipal(UserPrincipal user, Credential authorities) {
        return UserAuthPrincipal.builder()
                .credential(authorities)
                .user(user)
                .build();
    }

}
