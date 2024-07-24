package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.BookingRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.BookingResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.BookingResponseDTO;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBookingService {


    List<BookingResponseDTO> getAllBooking();
    BookingResponseDTO getBooking(String id);
    List<BookingResponseDTO> getAllBookingByUserId(String userId);
    BookingResponseDTO createBooking(BookingRequestDTO request);

    @Transactional
    BookingResponseDTO updateBookingStatus(String id, BookingStatus status);

    BookingResponseDTO updateBooking(String bookingId, BookingRequestDTO bookingRequest);
}
