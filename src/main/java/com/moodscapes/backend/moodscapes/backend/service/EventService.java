package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dao.EventRepo;
import com.moodscapes.backend.moodscapes.backend.dto.request.EventRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.mapper.EventMapper;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IEventService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EventService implements IEventService {

    private final EventRepo bookStoreRepo;
    private final EventMapper mapper;

    @Override
    public List<EventResponseDTO> getAllEvents() {
        return null;
    }

    @Override
    public EventResponseDTO getEventById(Long id) {
        return null;
    }

    @Override
    public EventResponseDTO addEvent(EventRequestDTO book) {
        return null;
    }

    @Override
    public EventResponseDTO updateEvent(Long id, EventRequestDTO updatedEvent) {
        return null;
    }

    @Override
    public void deleteEvent(Long id) {

    }
}
