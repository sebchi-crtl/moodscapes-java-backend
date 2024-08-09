package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.ClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Client;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EventClientMapper implements Function<Event, EventClientResponseDTO> {


    @Override
    public EventClientResponseDTO apply(Event event) {
//        ClientResponseDTO clients = new ClientResponseDTO(
//                client.getId(),
//                client.getEventId(),
//                client.getUserId(),
//                client.getFirstName(),
//                client.getLastName(),
//                client.getCountry(),
//                client.getPhoneNumber(),
//                client.getEmail(),
//                client.getBudget(),
//                client.getNotes(),
//                client.isActive()
//        );
        return new EventClientResponseDTO(
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
                event.getUpdatedBy(),
                "clients"
        );
    }


}
