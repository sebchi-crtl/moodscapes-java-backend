package com.moodscapes.backend.moodscapes.backend.dto.response;

import com.moodscapes.backend.moodscapes.backend.entity.Event;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingItemType;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record BookingResponseDTO(
        String id,
        String userId,
        String recipientUserId,
        Event event,
        String plannerName,
        BookingStatus confirm,
        BookingItemType itemType,
        List<BookingDetailResponseDTO> details
) {
}
