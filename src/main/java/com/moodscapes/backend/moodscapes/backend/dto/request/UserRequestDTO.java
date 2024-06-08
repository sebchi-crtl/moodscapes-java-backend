package com.moodscapes.backend.moodscapes.backend.dto.request;

import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserRequestDTO(
        @NotEmpty(message = "email ca not be null or empty")
        @Email(message = "invalid email address")
        String email,
        String fullName,
        Set<Role> role,
        String bio,
        Set<String> phoneNumber
) {
}

