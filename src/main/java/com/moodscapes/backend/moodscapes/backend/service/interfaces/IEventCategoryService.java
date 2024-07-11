package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventSharedResponseDTO;

import java.util.List;

public interface IEventCategoryService {
    List<EventResponseDTO> getAllEvents();
    List<EventResponseDTO> getAllEventsByUserId(String userId);
    EventResponseDTO getEventById(String id);
    boolean checkIfEventIdExists(String email);
    EventResponseDTO addEventCategory(EventRequestDTO book);
    EventResponseDTO updateEvent(String id, EventRequestDTO updatedEvent);
    void deleteEvent(String id);
    EventSharedResponseDTO listSharedUsersByEventId(String id);
}
