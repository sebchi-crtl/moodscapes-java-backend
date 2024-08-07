package com.moodscapes.backend.moodscapes.backend.dto.request;

import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;

import java.time.LocalDateTime;

public record EventRequestDTO(
        String userId,
        String title,
        String eventCategoryId,
        String location,
        LocalDateTime eventDate,
        String currency,
        String notes,
        String sharedUserId
) {
}

