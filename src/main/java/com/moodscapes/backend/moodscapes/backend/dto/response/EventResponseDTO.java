package com.moodscapes.backend.moodscapes.backend.dto.response;

import java.time.LocalDateTime;

public record EventResponseDTO(
        String id,
        String userId,
        String title,
        String location,
        boolean available,
        String note,
        LocalDateTime createdAt
) {
}
