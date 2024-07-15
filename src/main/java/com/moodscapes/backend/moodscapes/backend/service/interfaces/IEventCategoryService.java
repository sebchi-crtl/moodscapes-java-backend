package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.EventCategoryRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventCategoryResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventSharedResponseDTO;

import java.util.List;

public interface IEventCategoryService {
    List<EventCategoryResponseDTO> getAllEventCategory();
    List<EventCategoryResponseDTO> getAllEventCategoryByUserId(String userId);
    EventCategoryResponseDTO getEventCategoryById(String id);
//    boolean checkIfEventIdExists(String email);
    EventCategoryResponseDTO addEventCategory(EventCategoryRequestDTO request);
    EventCategoryResponseDTO updateEventCategory(String id, EventCategoryRequestDTO updatedEvent);
    void deleteEventCategory(String id);
}
