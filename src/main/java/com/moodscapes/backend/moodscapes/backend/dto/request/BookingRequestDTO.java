package com.moodscapes.backend.moodscapes.backend.dto.request;

public record BookingRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static BookingRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

