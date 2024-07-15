package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteItemResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import com.moodscapes.backend.moodscapes.backend.entity.Quotes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class QuoteMapper implements Function<Quotes, QuoteResponseDTO> {


    @Override
    public QuoteResponseDTO apply(Quotes quote) {
        List<QuoteItemResponseDTO> items = quote.getItems().stream().map(item ->
                new QuoteItemResponseDTO(
                        item.getVendorItemInfo(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalCost()
                )
        ).collect(Collectors.toList());
        return new QuoteResponseDTO(
                quote.getId(),
                quote.getUserId(),
                quote.getVendorName(),
                quote.getReceiverUserId(),
                quote.getEventName(),
                quote.getTotalCost(),
                quote.getStatus().name(),
                quote.getEventMessage(),
                items
        );
    }
}
