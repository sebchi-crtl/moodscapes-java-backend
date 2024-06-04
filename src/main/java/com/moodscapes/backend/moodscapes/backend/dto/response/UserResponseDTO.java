package com.moodscapes.backend.moodscapes.backend.dto.response;

import com.moodscapes.backend.moodscapes.backend.enumeration.Role;

import java.time.LocalDateTime;
import java.util.Set;

public record UserResponseDTO(
        String id,
        String email,
        String fullName,
        String bio,
        Set<String> phoneNumber,
        boolean enabled,
        String imageUrl,
        Set<Role> role,
        String address
) {

}
