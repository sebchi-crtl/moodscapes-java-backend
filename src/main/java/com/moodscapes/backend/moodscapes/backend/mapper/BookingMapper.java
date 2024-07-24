package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.BookingDetailResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.BookingResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Booking;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookingMapper implements Function<Booking, BookingResponseDTO> {


    @Override
    public BookingResponseDTO apply(Booking booking) {
        List<BookingDetailResponseDTO> details = booking.getDetails().stream()
                .map(detail -> new BookingDetailResponseDTO(
                        detail.getId(),
                        detail.getItemId(),
                        detail.getItemName(),
                        detail.getVendorName(),
                        detail.getQuantity(),
                        detail.getImageUrl(),
                        detail.getUnitPrice(),
                        detail.getTotalCost()
                ))
                .collect(Collectors.toList());
//        EventResponseDTO event = new EventResponseDTO(
//            booking.getEvent().getId()
//        )

        return new BookingResponseDTO(
                booking.getId(),
                booking.getUserId(),
                booking.getRecipientUserId(),
                booking.getEvent(),
                booking.getPlannerName(),
                booking.getConfirm(),
                booking.getItemType(),
                details
        );
    }
}
