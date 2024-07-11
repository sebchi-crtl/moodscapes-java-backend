package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventSharedResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventSharedMapper implements Function<Event, EventSharedResponseDTO> {


    @Override
    public EventSharedResponseDTO apply(Event event) {
        return new EventSharedResponseDTO(
                event.getId(),
                event.getUserId(),
                event.getSharedUserId()
        );
    }
}
