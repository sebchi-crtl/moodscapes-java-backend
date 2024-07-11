package com.moodscapes.backend.moodscapes.backend.mapper;

import com.moodscapes.backend.moodscapes.backend.dto.response.ClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Client;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ClientMapper implements Function<Client, ClientResponseDTO> {


    @Override
    public ClientResponseDTO apply(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getEventID(),
                client.getUserID(),
                client.getFirstName(),
                client.getLastName(),
                client.getCountry(),
                client.getPhoneNumber(),
                client.getEmail(),
                client.getBudget(),
                client.getNotes(),
                client.isActive()
        );
    }
}
