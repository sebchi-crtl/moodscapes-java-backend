package com.moodscapes.backend.moodscapes.backend.util;

import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;

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
}
