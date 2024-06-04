package com.moodscapes.backend.moodscapes.backend.dto.request;

import com.moodscapes.backend.moodscapes.backend.enumeration.Role;

import java.util.Set;

public record UserRequestDTO(
        String email,
        String fullName,
        Set<String> phoneNumber,
        String imageUrl,
        Set<Role> role,
        String address
) {
    public static UserRequestDTO of(String email, String fullName, Set<String> phoneNumber, String imageUrl, Set<Role> role, String address) {
        return null;
    }
}

