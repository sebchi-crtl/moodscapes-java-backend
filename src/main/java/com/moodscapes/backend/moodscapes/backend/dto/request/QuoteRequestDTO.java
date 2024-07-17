package com.moodscapes.backend.moodscapes.backend.dto.request;

import java.util.List;

public record QuoteRequestDTO(
        String userId,
        String vendorName,
        String receiverUserId,
        String eventId,
        String eventName,
        List<QuoteItemRequest> items
) { }

