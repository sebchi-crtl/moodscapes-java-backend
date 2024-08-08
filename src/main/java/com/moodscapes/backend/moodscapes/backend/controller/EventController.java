package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.ClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
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
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.handleErrorRequest;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/event")
@CrossOrigin
public class EventController {
    private final IEventService eventService;

    @GetMapping("details")
    public String getEvent() {
        return "Event details";
    }

    @GetMapping("/shared_user/{id}")
    public ResponseEntity<HttpResponse> getSharedUsersByEventId(@PathVariable("userId") String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            var listSharedUsersByEventId = eventService.listSharedUsersByEventId(id);
            if (listSharedUsersByEventId == null)
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT, FETCHING_ERROR
                        ), DATA_USER_FETCHING, OK));
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            EVENT, listSharedUsersByEventId
                    ), EVENT_USER_FETCHING, OK));
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
    public ResponseEntity<HttpResponse> getAllEventsByUser(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<EventResponseDTO> allEventsByUser = eventService.getAllEventsByUserId(userId);
            if (allEventsByUser.isEmpty())
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT, FETCHING_ERROR
                        ), DATA_USER_FETCHING, OK));
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            EVENT, allEventsByUser
                    ), EVENT_USER_FETCHING, OK));
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
    public  ResponseEntity<HttpResponse> getAllEvents(HttpServletRequest request, HttpServletResponse response) {
        try{
            var allEvents = eventService.getAllEvents();
            if (!allEvents.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT, allEvents
                        ), FETCH_SUCCESS, OK));
            }
            return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(EVENT, FETCHING_ERROR),NO_RECORD,OK));
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
    public ResponseEntity<HttpResponse> getEventById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            var event = eventService.getEventById(id);
            if (event != null)
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT, event
                        ), FETCH_SUCCESS, OK));
            return ResponseEntity.ok()
                    .body(getResponse(request,Map.of(EVENT, FETCHING_ERROR),NO_RECORD,OK));

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
    public ResponseEntity<HttpResponse> addEvent(@RequestBody EventRequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        try {
            var event = eventService.addEvent(requestDTO);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+EVENT, event
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
    @PostMapping("/client")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<HttpResponse> addEventClient(@RequestBody EventRequestDTO requestDTO, @RequestBody ClientRequestDTO requestClientDTO, HttpServletRequest request, HttpServletResponse response) {

        try {
            var event = eventService.addEventClient(requestDTO, requestClientDTO);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+EVENT, event
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
    @PutMapping("/shared_user/{eventId}")
    public ResponseEntity<HttpResponse> addSharedUser(@PathVariable String eventId, @RequestBody String userId,  @RequestBody String sharedUserId, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+eventId);
            var sharedUser = eventService.addSharedUser(eventId, userId, sharedUserId);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "added_shared_"+EVENT, sharedUser
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
    public ResponseEntity<HttpResponse> updateEvent(@PathVariable String id, @RequestBody EventRequestDTO updatedGuest, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+id);
            var update = eventService.updateEvent(id, updatedGuest);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+EVENT, update
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
            var delete = eventService.deleteEvent(id);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "delete_"+EVENT, delete
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
