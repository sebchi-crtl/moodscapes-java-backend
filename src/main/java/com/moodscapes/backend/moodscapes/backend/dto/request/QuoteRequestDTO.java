package com.moodscapes.backend.moodscapes.backend.dto.request;

public record QuoteRequestDTO(
        String title,
        String author,
        boolean available
) {
    public static QuoteRequestDTO of(String title, String author, boolean available) {
        return null;
    }
}

