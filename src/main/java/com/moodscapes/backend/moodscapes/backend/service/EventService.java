package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.dto.request.ClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventSharedResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Client;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.mapper.EventClientMapper;
import com.moodscapes.backend.moodscapes.backend.mapper.EventMapper;
import com.moodscapes.backend.moodscapes.backend.mapper.EventSharedMapper;
import com.moodscapes.backend.moodscapes.backend.repository.ClientRepo;
import com.moodscapes.backend.moodscapes.backend.repository.EventCategoryRepo;
import com.moodscapes.backend.moodscapes.backend.repository.EventRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IClientService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;
import static java.util.Collections.emptyList;

@Service
@AllArgsConstructor
@Slf4j
public class EventService implements IEventService {

    private final EventRepo repository;
    private final EventCategoryRepo categoryRepository;
    private final ClientRepo ClientRepository;
    private final UserService userService;
    private final EventMapper mapper;
    private final EventClientMapper mapperClient;
    private final EventSharedMapper mapperShared;

    @Override
    public List<EventResponseDTO> getAllEvents() {
        log.info("fetching all events");
        return repository.findAll().stream().map(mapper).collect(Collectors.toList());
    }
//    @Override
//    public List<EventResponseDTO> listEventsByEventCategoryId(String category) {
//        var eventDTOs = repository
//                .findByEventCategory(category)
//                .stream()
//                .map(mapper)
//                .collect(Collectors.toList());
//        return eventDTOs;
//    }
    @Override
    public List<EventResponseDTO> getAllEventsByUserId(String userId) {
        log.info("fetching all events by user's id");
        try {
            return repository.findByUserId(userId).stream().toList();
        }
        catch (Exception ex){
            throw new ApiException(UNABLE_FETCH);
        }
    }

    @Override
    public EventResponseDTO getEventById(String id) {
        var event = repository.findById(id).orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
        return mapper.apply(event);
    }

    @Override
    public boolean checkIfEventIdExists(String id) {
        return repository.existsById(id);
    }

    @Override
    public EventResponseDTO addEvent(EventRequestDTO request) {
        try {
            log.info("creating event: " + request);
            var userId = userService.getUserById(request.userId());
            EventCategory category = categoryRepository.findById(request.eventCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            if (userId) {
                RequestContext.setUserId(request.userId());
                var build = Event
                        .builder()
                        .userId(request.userId())
                        .title(request.title())
                        .eventCategory(category)
                        .location(request.location())
                        .eventDate(request.eventDate())
                        .currency(request.currency())
                        .notes(request.notes())
                        .sharedUserId(emptyList())
                        .build();
                log.info("Saving event: {}", build);
                var savedEvent = repository.save(build);
                log.info("Event saved successfully: {}", savedEvent);
                return mapper.apply(savedEvent);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (DataIntegrityViolationException exm) {
            log.error("Error adding event due to data integrity violation: {}", exm.getMessage(), exm);
            throw new ApiException("Failed to add event due to data integrity violation");
        }
        catch (Exception ex) {
            log.error("Error adding event: {}", ex.getMessage(), ex);
            throw new ApiException(ex.getMessage());
        }
        finally {
            RequestContext.start();
        }
    }
      @Override
      public EventResponseDTO addEventClient(EventClientRequestDTO request) {
        try {
            log.info("creating event: " + request);
            var userId = userService.getUserById(request.userId());
            EventCategory category = categoryRepository.findById(request.eventCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            if (userId) {
                RequestContext.setUserId(request.userId());
                var build = Event
                        .builder()
                        .userId(request.userId())
                        .title(request.title())
                        .eventCategory(category)
                        .location(request.location())
                        .eventDate(request.eventDate())
                        .currency(request.currency())
                        .notes(request.eventNotes())
                        .sharedUserId(emptyList())
                        .build();
                log.info("Saving event: {}", build);
                var savedEvent = repository.saveAndFlush(build);
                log.info("Event saved successfully: {}", savedEvent);
                String eventId = savedEvent.getId();
                var client = Client
                        .builder()
                        .eventId(eventId)
                        .userId(request.userId())
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .country(request.country())
                        .phoneNumber(request.phoneNumber())
                        .email(request.email())
                        .budget(request.budget())
                        .notes(request.clientNotes())
                        .active(true)
                        .build();
                ClientRepository.save(client);
                log.info("Client saved successfully: {}", savedEvent);
                return mapper.apply(savedEvent);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (DataIntegrityViolationException exm) {
            log.error("Error adding event due to data integrity violation: {}", exm.getMessage(), exm);
            throw new ApiException("Failed to add event due to data integrity violation");
        }
        catch (Exception ex) {
            log.error("Error adding event: {}", ex.getMessage(), ex);
            throw new ApiException(ex.getMessage());
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    public EventResponseDTO updateEvent(String id, EventRequestDTO request) {
        try {
            log.info("updating event: " + request);
            EventCategory category = categoryRepository.findById(request.eventCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
            var event = repository.findById(id)
                    .orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
            var userId = userService.getUserById(event.getUserId());
            RequestContext.setUserId(request.userId());
            if (userId) {
                var updatedEvent = Event
                        .builder()
                        .userId(event.getUserId())
                        .title(request.title() != null ? request.title() : event.getTitle())
                        .eventCategory(category != null ? category : event.getEventCategory())
                        .location(request.location() != null ? request.location() : event.getLocation())
                        .eventDate(request.eventDate() != null ? request.eventDate() : event.getEventDate())
                        .currency(request.currency() != null ? request.currency() : event.getCurrency())
                        .notes(request.notes() != null ? request.notes() : event.getNotes())
                        .build();
                var savedEvent = repository.save(updatedEvent);
                return mapper.apply(savedEvent);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (Exception ex){
            throw new ApiException(UPDATE_FETCHING_ERROR + "Event");
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    public String deleteEvent(String id) {
        log.info("deleting event");
        var guest = repository.findById(id).orElse(null);
        if (guest == null) throw new RequestValidationException(REQUEST_VALIDATION_ERROR + id);
        repository.deleteById(id);
        return "success";
    }

    @Override
    public EventSharedResponseDTO listSharedUsersByEventId(String id) {
        var event = repository.findById(id).orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
        return mapperShared.apply(event);
    }

    @Override
    public EventSharedResponseDTO addSharedUser(String eventId, String userId, String sharedUserId) {
        try {
            log.info("sending shared users to event: " + sharedUserId);
            RequestContext.setUserId(userId);
            var event = repository.findById(eventId).orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + eventId));
            if (!event.getSharedUserId().contains(sharedUserId)) {
                event.getSharedUserId().add(sharedUserId);
                repository.save(event);
            }
            return mapperShared.apply(event);
        }
        catch (Exception ex){
            throw new ApiException(FETCHING_ERROR + "Event");
        }
        finally {
            RequestContext.start();
        }
    }
}