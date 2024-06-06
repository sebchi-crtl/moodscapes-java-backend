package com.moodscapes.backend.moodscapes.backend.util;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class UserUtils {

    public static User createUserEntity(String email, String fullName, Set<Role> role){
         // Assuming username is the user ID
        String userId=UUID.randomUUID().toString();
//        RequestContext.start();
//        RequestContext.setUserId(userId);
        return User
                .builder()
                .userId(userId)
                .email(email)
                .fullName(fullName)
                .bio(EMPTY)
                .phoneNumber(Collections.singleton(EMPTY))
                .enabled(true)
                .imageUrl("https://upload.wikimedia.org/wikipedia/commons/6/67/User_Avatar.png")
                .role(role)
                .address(EMPTY)
                .build();
    }
}
