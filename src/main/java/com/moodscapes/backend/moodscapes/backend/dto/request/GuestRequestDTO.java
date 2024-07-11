package com.moodscapes.backend.moodscapes.backend.dto.request;

public record GuestRequestDTO(
        String userId,
        String eventId,
        String firstName,
        String lastName,
        String grouping,
        boolean rsvp,
        String mealPreference,
        String notes
) { }

