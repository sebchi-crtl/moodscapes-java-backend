package com.moodscapes.backend.moodscapes.backend.dto.response;

import java.time.LocalDateTime;

public record ClientResponseDTO(
        String id,
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
) {
}
