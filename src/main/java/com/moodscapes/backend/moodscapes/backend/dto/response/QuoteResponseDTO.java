package com.moodscapes.backend.moodscapes.backend.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record QuoteResponseDTO(
        String id,
        String userId,
        String vendorName,
        String receiverUserId,
        String eventName,
        Double totalCost,
        String status,
        String eventMessage,
        List<QuoteItemResponseDTO> items
) {
}

