package com.moodscapes.backend.moodscapes.backend.dto.request;

import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;

import java.time.LocalDateTime;

public record EventClientRequestDTO(
        String userId,
        String title,
        String eventCategoryId,
        String location,
        LocalDateTime eventDate,
        String currency,
        String eventNotes,
        String firstName,
        String lastName,
        String country,
        String phoneNumber,
        String email,
        float budget,
        String clientNotes
) {
}

