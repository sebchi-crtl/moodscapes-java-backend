package com.moodscapes.backend.moodscapes.backend.dto.request;

public record EventCategoryRequestDTO(
        String userId,
        String name,
        String description
) {
}

