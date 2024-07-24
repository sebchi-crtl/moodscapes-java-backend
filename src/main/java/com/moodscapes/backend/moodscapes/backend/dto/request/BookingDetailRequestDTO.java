package com.moodscapes.backend.moodscapes.backend.dto.request;

import java.util.List;

public record BookingDetailRequestDTO(
        String itemId,
        String itemName,
        String vendorName,
        String imageUrl,
        Integer quantity,
        Double unitPrice,
        Double totalCost
) {}

