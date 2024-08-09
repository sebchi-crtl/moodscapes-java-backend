package com.moodscapes.backend.moodscapes.backend.dto.response;

import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;

import java.time.LocalDateTime;
import java.util.List;

public record EventClientResponseDTO(
        String id,
        String userId,
        String title,
        EventCategory eventCategory,
        String location,
        LocalDateTime eventDate,
        String currency,
        String notes,
        List<String> sharedUserId,
        String createdBy,
        String updatedBy,
        String clients
) {
}
