package com.moodscapes.backend.moodscapes.backend.dto.request;

public record AuthRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static AuthRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

