package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.ClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventSharedResponseDTO;

import java.util.List;

public interface IEventService {

    List<EventResponseDTO> getAllEvents();
    List<EventResponseDTO> getAllEventsByUserId(String userId);
    EventResponseDTO getEventById(String id);

    boolean checkIfEventIdExists(String email);

    EventResponseDTO addEvent(EventRequestDTO eventRequestDTO);
    EventResponseDTO addEventClient(EventClientRequestDTO request);
    EventResponseDTO updateEvent(String id, EventRequestDTO updatedEvent);
    String deleteEvent(String id);
    EventSharedResponseDTO listSharedUsersByEventId(String id);
//    List<EventResponseDTO> listEventsByEventCategoryId(String category);
    EventSharedResponseDTO addSharedUser(String eventId, String userId, String sharedUserId);
}
