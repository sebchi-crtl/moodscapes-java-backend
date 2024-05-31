package com.moodscapes.backend.moodscapes.backend.dto.request;

public record UserRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static UserRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

