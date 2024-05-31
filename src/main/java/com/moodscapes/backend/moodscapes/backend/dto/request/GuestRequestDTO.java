package com.moodscapes.backend.moodscapes.backend.dto.request;

public record GuestRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static GuestRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

