package com.moodscapes.backend.moodscapes.backend.dto.request;

public record BookingDetailRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static BookingDetailRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

