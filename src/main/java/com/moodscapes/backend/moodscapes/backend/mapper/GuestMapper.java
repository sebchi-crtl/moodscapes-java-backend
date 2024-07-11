package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Guest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GuestMapper implements Function<Guest, GuestResponseDTO> {

    @Override
    public GuestResponseDTO apply(Guest guest) {
        return new GuestResponseDTO(
                guest.getId(),
                guest.getUserId(),
                guest.getEventId(),
                guest.getFirstName(),
                guest.getLastName(),
                guest.getGrouping(),
                guest.isRsvp(),
                guest.getMealPreference(),
                guest.getNotes()
        );
    }
}
