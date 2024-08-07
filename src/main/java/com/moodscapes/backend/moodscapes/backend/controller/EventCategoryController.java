package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.EventCategoryRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventCategoryResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventCategoryService;
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
@RequestMapping("/event-category")
public class EventCategoryController {
    private final IEventCategoryService eventService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<HttpResponse> getAllEventCategoryByUser(@PathVariable("userId") String userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<EventCategoryResponseDTO> allEventsByUser = eventService.getAllEventCategoryByUserId(userId);
            if (allEventsByUser.isEmpty())
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT+"_category", FETCHING_ERROR
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
    public  ResponseEntity<HttpResponse> getAllEventCategory(HttpServletRequest request, HttpServletResponse response) {
        try{
            var allEventCategory = eventService.getAllEventCategory();
            if (!allEventCategory.isEmpty()){
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT+"_category", allEventCategory
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
    public ResponseEntity<HttpResponse> getEventCategoryById(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            var eventCategoryById = eventService.getEventCategoryById(id);
            if (eventCategoryById != null)
                return ResponseEntity
                        .ok()
                        .body(getResponse(request, Map.of(
                                EVENT+"_category", eventCategoryById
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
    public ResponseEntity<HttpResponse> addEventCategory(@RequestBody EventCategoryRequestDTO requestDTO, HttpServletRequest request, HttpServletResponse response) {

        try {
            var eventCategory = eventService.addEventCategory(requestDTO);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "create_"+EVENT+"_category", eventCategory
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
    public ResponseEntity<HttpResponse> updateEventCategory(@PathVariable String id, @RequestBody EventCategoryRequestDTO updatedEventCategory, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to update: "+id);
            var update = eventService.updateEventCategory(id, updatedEventCategory);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "update_"+EVENT+"_category", update
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
    public ResponseEntity<HttpResponse> deleteEventCategory(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("selecting id to delete: "+id);
            eventService.deleteEventCategory(id);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "delete_"+EVENT+"_category", "deletion successful"
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
