package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventCategoryRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventCategoryResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.mapper.EventCategoryMapper;
import com.moodscapes.backend.moodscapes.backend.mapper.EventSharedMapper;
import com.moodscapes.backend.moodscapes.backend.repository.EventCategoryRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventCategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class EventCategoryService implements IEventCategoryService {

    private final EventCategoryRepo repo;
    private final UserService userService;
    private final EventCategoryMapper mapper;
    private final EventSharedMapper mapperShared;

    @Override
    public List<EventCategoryResponseDTO> getAllEventCategory() {
        log.info("fetching all categories");
        return repo.findAll().stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public List<EventCategoryResponseDTO> getAllEventCategoryByUserId(String userId) {
        log.info("fetching all event categories by user's id");
        try {
            return repo.findByUserId(userId).stream().toList();
        }
        catch (Exception ex){
            throw new ApiException(UNABLE_FETCH);
        }
    }

    @Override
    public EventCategoryResponseDTO getEventCategoryById(String id) {
        var event = repo.findById(id).orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
        return mapper.apply(event);
    }

//    @Override
//    public boolean checkIfEventIdExists(String id) {
//        return repo.existsById(id);
//    }

    @Override
    public EventCategoryResponseDTO addEventCategory(EventCategoryRequestDTO request) {
        try {
            log.info("creating event category: " + request);
            var userId = userService.getUserById(request.userId());
            if (userId) {
                RequestContext.setUserId(request.userId());
                var build = EventCategory
                        .builder()
                        .userId(request.userId())
                        .name(request.name())
                        .description(request.description())
                        .build();
                log.info("Saving event category: {}", build);
                var savedEventCategory = repo.save(build);
                log.info("Event category saved successfully: {}", savedEventCategory);
                return mapper.apply(savedEventCategory);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (DataIntegrityViolationException exm) {
            log.error("Error adding category due to data integrity violation: {}", exm.getMessage(), exm);
            throw new ApiException("Failed to add event category due to data integrity violation");
        }
        catch (Exception ex) {
            log.error("Error adding event category: {}", ex.getMessage(), ex);
            throw new ApiException(ex.getMessage());
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    public EventCategoryResponseDTO updateEventCategory(String id, EventCategoryRequestDTO request) {
        try {
            log.info("updating guest: " + request);
            var eventCategory = repo.findById(id)
                    .orElseThrow(() -> new RequestValidationException(REQUEST_VALIDATION_ERROR + id));
            var userId = userService.getUserById(eventCategory.getUserId());
            RequestContext.setUserId(request.userId());
            if (userId) {
                var updatedEventCategory = EventCategory
                        .builder()
                        .userId(eventCategory.getUserId())
                        .name(request.name() != null ? request.name() : eventCategory.getName())
                        .description(request.description() != null ? request.description() : eventCategory.getDescription())
                        .build();
                var savedEventCategory = repo.save(updatedEventCategory);
                return mapper.apply(savedEventCategory);
            }
            else throw new ApiException(USER_FETCHING_ERROR);
        }
        catch (Exception ex){
            throw new ApiException(UPDATE_FETCHING_ERROR + "Event Category");
        }
        finally {
            RequestContext.start();
        }
    }

    @Override
    public void deleteEventCategory(String id) {
        log.info("deleting event");
        var category = repo.findById(id).orElse(null);
        if (category == null) throw new RequestValidationException(REQUEST_VALIDATION_ERROR + id);
        repo.deleteById(id);
    }


}
