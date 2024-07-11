package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventMapper implements Function<Event, EventResponseDTO> {


    @Override
    public EventResponseDTO apply(Event event) {
        return new EventResponseDTO(
                event.getId(),
                event.getUserId(),
                event.getTitle(),
                event.getEventCategory(),
                event.getLocation(),
                event.getEventDate(),
                event.getCurrency(),
                event.getNotes(),
                event.getSharedUserId(),
                event.getCreatedBy(),
                event.getUpdatedBy()
        );
    }
}
