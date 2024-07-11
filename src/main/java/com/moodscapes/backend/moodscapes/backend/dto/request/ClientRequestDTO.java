package com.moodscapes.backend.moodscapes.backend.dto.request;

public record ClientRequestDTO(
        String eventId,
        String userId,
        String firstName,
        String lastName,
        String country,
        String phoneNumber,
        String email,
        float budget,
        String notes,
        boolean active
) { }
