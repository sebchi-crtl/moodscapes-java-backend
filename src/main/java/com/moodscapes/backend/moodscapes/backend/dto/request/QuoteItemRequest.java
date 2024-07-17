package com.moodscapes.backend.moodscapes.backend.dto.request;

public record QuoteItemRequest(
        String vendorItemInfo,  // includes item name, vendor name, and image URL in 2D
        Integer quantity,
        Double unitPrice
) {
}
