package com.moodscapes.backend.moodscapes.backend.dto.response;

public record GuestResponseDTO(
        String id,
        String userId,
        String eventId,
        String firstName,
        String lastName,
        String grouping,
        boolean rsvp,
        String mealPreference,
        String notes
) {
}
