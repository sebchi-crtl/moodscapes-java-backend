package com.moodscapes.backend.moodscapes.backend.dto.request;

public record ClientRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static ClientRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}
