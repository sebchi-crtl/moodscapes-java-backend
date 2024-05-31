package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;

import java.util.List;

public interface IEventService {

    List<EventResponseDTO> getAllEvents();
    EventResponseDTO getEventById(Long id);
    EventResponseDTO addEvent(EventRequestDTO book);
    EventResponseDTO updateEvent(Long id, EventRequestDTO updatedEvent);
    void deleteEvent(Long id);
}
