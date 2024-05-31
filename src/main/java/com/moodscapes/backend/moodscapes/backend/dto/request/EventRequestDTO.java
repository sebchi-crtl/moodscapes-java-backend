package com.moodscapes.backend.moodscapes.backend.dto.request;

public record EventRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static EventRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

