package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventCategoryResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventCategoryMapper implements Function<EventCategory, EventCategoryResponseDTO> {


    @Override
    public EventCategoryResponseDTO apply(EventCategory event) {
        return new EventCategoryResponseDTO(
                event.getId(),
                event.getUserId(),
                event.getName(),
                event.getDescription(),
                event.getCreatedBy(),
                event.getUpdatedBy()
        );
    }
}
