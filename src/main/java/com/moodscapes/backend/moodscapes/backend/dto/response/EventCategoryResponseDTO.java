package com.moodscapes.backend.moodscapes.backend.dto.response;

public record EventCategoryResponseDTO(
        String id,
        String userId,
        String name,
        String description,
        String createdBy,
        String updatedBy
) {
}
