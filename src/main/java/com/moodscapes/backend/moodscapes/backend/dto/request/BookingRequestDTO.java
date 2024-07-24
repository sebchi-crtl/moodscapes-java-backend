package com.moodscapes.backend.moodscapes.backend.dto.request;

import com.moodscapes.backend.moodscapes.backend.enumeration.BookingItemType;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;

import java.util.List;

public record BookingRequestDTO(
        String userId,
        String recipientUserId,
        String eventId,
        String plannerName,
        BookingStatus status,
        BookingItemType itemType,
        List<BookingDetailRequestDTO> details
) {}

