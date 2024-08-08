package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.BookingRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.BookingResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IBookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.handleErrorRequest;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping("/bookings")
@RequiredArgsConstructor
@CrossOrigin
public class BookingController {
    private final IBookingService bookingService;

    @GetMapping("/details/user")
    @PreAuthorize("hasRole('USER')")
    public String userEnd() {
        return "Event details";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEnd() {
        return "you can view";
    }
    @GetMapping("/details")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public String adminUserEnd() {
        return "you can view";
    }
    @GetMapping("/public")
    public String publicEnd() {
        return "you can view this ";
    }
    @PostMapping
    public ResponseEntity<HttpResponse> createBooking(@RequestBody BookingRequestDTO bookingRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            var createBooking = bookingService.createBooking(bookingRequest);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+BOOKINGS, createBooking
                    ), CREATE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getBooking(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            var booking = bookingService.getBooking(id);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+BOOKINGS, booking
                    ), CREATE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<HttpResponse> getBookingByUserId(@PathVariable String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<BookingResponseDTO> bookingByUserId = bookingService.getAllBookingByUserId(userId);
            if (bookingByUserId.isEmpty())
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                BOOKINGS, FETCHING_ERROR
                        ), USER_FETCHING_ERROR, OK));
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            BOOKINGS, bookingByUserId
                    ), DATA_USER_FETCHING, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getAllBookings(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("getting all bookings: ");
            var booking = bookingService.getAllBooking();
            if (!booking.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                BOOKINGS, booking
                        ), FETCH_SUCCESS, OK));
            }
            return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(QUOTES, FETCHING_ERROR),NO_RECORD,OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<HttpResponse> updateBookingStatus(@PathVariable String id, @RequestParam BookingStatus status, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+id);
            var update = bookingService.updateBookingStatus(id, status);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+BOOKINGS+"_status", update
                    ), UPDATE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateBooking(@PathVariable String id, @RequestParam BookingRequestDTO bookingRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+id);
            var update = bookingService.updateBooking(id, bookingRequest);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+BOOKINGS+"_status", update
                    ), UPDATE_SUCCESS, OK));
        }
        catch (AccessDeniedException ade){
            log.error("Error writing response: {}", ade.getMessage());
            handleErrorRequest(request, response, ade);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

//    @PostMapping("/{id}/message")
//    public void sendMessageToClient(@PathVariable String id, @RequestBody MessageRequest messageRequest) {
//        // Implement messaging logic here
//        // You might want to add a service method for sending messages
//    }
}
