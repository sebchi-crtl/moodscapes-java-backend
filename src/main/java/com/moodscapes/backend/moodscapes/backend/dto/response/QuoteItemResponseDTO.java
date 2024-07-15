package com.moodscapes.backend.moodscapes.backend.dto.response;

public record QuoteItemResponseDTO(
        String vendorItemInfo,  // includes item name, vendor name, and image URL in 2D
        Integer quantity,
        Double unitPrice,
        Double totalCost
) {
}
