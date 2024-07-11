package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.GuestRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IGuestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping("/guest")
@AllArgsConstructor
public class GuestController {
    private final IGuestService guestService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<HttpResponse> getAllGuestsByUser(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<GuestResponseDTO> allGuestsByUser = guestService.getAllGuestsByUserId(userId);
            if (allGuestsByUser.isEmpty())
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                GUEST, GUEST_FETCHING_ERROR
                        ), GUEST_USER_FETCHING_ERROR, OK));
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            GUEST, allGuestsByUser
                    ), GUEST_USER_FETCHING, OK));
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
    public  ResponseEntity<HttpResponse> getAllGuests(HttpServletRequest request, HttpServletResponse response) {
        try{
            var allGuest = guestService.getAllGuests();
            if (!allGuest.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                GUEST, allGuest
                        ), FETCH_SUCCESS, OK));
            }
            return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(GUEST, GUEST_FETCHING_ERROR),NO_RECORD,OK));
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
    public ResponseEntity<HttpResponse> getGuestById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            var guest = guestService.getGuestById(id);
            if (guest != null)
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                GUEST, guest
                        ), FETCH_SUCCESS, OK));
        return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(GUEST, GUEST_FETCHING_ERROR),NO_RECORD,OK));

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
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpResponse> addGuest(@RequestBody GuestRequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        try {
            var addGuest = guestService.addGuest(requestDTO);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+GUEST, addGuest
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

    @PutMapping("/{id}")
    public ResponseEntity<HttpResponse> updateGuest(@PathVariable String id, @RequestBody GuestRequestDTO  updatedGuest, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+id);
            var update = guestService.updateGuest(id, updatedGuest);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+GUEST, update
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

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteGuest(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to delete: "+id);
            var delete = guestService.deleteGuest(id);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "delete_"+GUEST, delete
                    ), DELETE_SUCCESS, OK));
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
}
