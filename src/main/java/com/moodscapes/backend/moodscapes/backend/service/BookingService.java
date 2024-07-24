package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.dto.request.BookingRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.QuoteRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.BookingResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.*;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.mapper.BookingMapper;
import com.moodscapes.backend.moodscapes.backend.repository.*;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IBookingService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;
import static com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus.PENDING;

@Service
@Slf4j
public class BookingService implements IBookingService {
    @Autowired
    private BookingRepo bookRepository;
    @Autowired
    private BookingDetailRepo bookingDetailsRepository;
    @Autowired
    private EventRepo eventRepository;
    @Autowired
    private BookingMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private IEventService eventService;

    private QuoteItem findQuoteItemByVendorItemInfo(List<QuoteItem> items, String vendorItemInfo) {
        return items.stream().filter(item -> item.getVendorItemInfo().equals(vendorItemInfo)).findFirst().orElse(null);
    }

    @Override
    public List<BookingResponseDTO> getAllBooking() {
        return bookRepository.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public BookingResponseDTO getBooking(String id) {
        var booking = bookRepository.findById(id)
                .orElseThrow(() -> new ApiException());
        return mapper.apply(booking);
    }

    @Override
    public List<BookingResponseDTO> getAllBookingByUserId(String userId) {
        log.info("Fetching all booking by user's id");
        try {
            return bookRepository
                    .findByUserId(userId)
                    .stream()
                    .map(mapper)
                    .collect(Collectors.toList());
        }
        catch (Exception ex) {
            throw new ApiException(UNABLE_FETCH);
        }
    }

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        try{

            log.info("creating quote: " + request);
            var userId = userService.getUserById(request.userId());
            var eventId = eventService.checkIfEventIdExists(request.eventId());
            Event event = eventRepository.findById(request.eventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));
            if (userId && eventId) {
                RequestContext.setUserId(request.userId());

                var booking = Booking.builder()
                        .userId(request.userId())
                        .recipientUserId(request.recipientUserId())
                        .event(event)
                        .plannerName(request.plannerName())
                        .confirm(PENDING)
                        .itemType(request.itemType())
                        .build();

                Booking finalBooking = booking;
                List<BookingDetail> details = request.details().stream().map(itemRequest ->
                        BookingDetail.builder()
                                .itemId(itemRequest.itemId())
                                .itemName(itemRequest.itemName())
                                .vendorName(itemRequest.vendorName())
                                .imageUrl(itemRequest.imageUrl())
                                .quantity(itemRequest.quantity())
                                .unitPrice(itemRequest.unitPrice())
                                .totalCost(itemRequest.totalCost())
                                .booking(finalBooking)
                                .build()
                ).collect(Collectors.toList());
                booking.setDetails(details);
                booking = bookRepository.save(booking);
                bookingDetailsRepository.saveAll(details);
                return mapper.apply(booking);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (DataIntegrityViolationException ex) {
            log.error("Error adding guest due to data integrity violation: {}", ex.getMessage(), ex);
            throw new ApiException("Failed to add guest due to data integrity violation");
        }
        catch (Exception ex) {
            log.error("Error adding guest: {}", ex.getMessage(), ex);
            throw new ApiException(ex.getMessage());
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    @Transactional
    public BookingResponseDTO updateBookingStatus(String id, BookingStatus status) {
        Booking booking = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setConfirm(status);
        booking = bookRepository.save(booking);
        return mapper.apply(booking);
    }

    @Override
    public BookingResponseDTO updateBooking(String bookingId, BookingRequestDTO bookingRequest) {
        return null;
    }
}
