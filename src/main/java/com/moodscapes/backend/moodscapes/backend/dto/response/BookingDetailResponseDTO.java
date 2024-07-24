package com.moodscapes.backend.moodscapes.backend.dto.response;

import java.time.LocalDateTime;

public record BookingDetailResponseDTO(
        String id,
        String itemId,
        String itemName,
        String vendorName,
        Integer quantity,
        String imageUrl,
        Double unitPrice,
        Double totalCost
) {
}
