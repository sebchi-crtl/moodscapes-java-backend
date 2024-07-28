package com.moodscapes.backend.moodscapes.backend.util;

import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@RequiredArgsConstructor
public class UserUtils {

    public static User createUserEntity(String email, String fullName, Set<Role> role){

        String userId=UUID.randomUUID().toString();
        return User
                .builder()
                .userId(userId)
                .email(email)
                .fullName(fullName)
                .bio(EMPTY)
                .phoneNumber(Collections.singleton(EMPTY))
                .enabled(false)
                .imageUrl("https://upload.wikimedia.org/wikipedia/commons/6/67/User_Avatar.png")
                .role(role)
                .address(EMPTY)
                .build();
    }

    public static UserPrincipal fromUser(User user) {
        return UserPrincipal.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .bio(user.getBio())
                .phoneNumber(user.getPhoneNumber().stream().findFirst().orElse(""))
                .imageUrl(user.getImageUrl())
                .role(user.getRole().stream().findFirst().map(Role::name).orElse(""))
                .authorities(user.getRole().stream().map(role -> new SimpleGrantedAuthority(role.name())).toList().toString())
                .enabled(user.isEnabled())
                .build();
    }

    public static UserPrincipal fromUserPrincipal(User user, Role role, Credential userCredentialById) {
        UserPrincipal userPrincipal = new UserPrincipal();
        BeanUtils.copyProperties(user, userPrincipal);
        userPrincipal.setId(user.getId());
        userPrincipal.setCreatedAt(user.getCreatedAt().toString());
        userPrincipal.setUpdatedAt(user.getUpdatedAt().toString());
        userPrincipal.setRole(role.name());
        userPrincipal.setAuthorities(String.valueOf(Role.valueOf(user.getRole().toString()).getAuthorities()));
        return userPrincipal;
    }


}
