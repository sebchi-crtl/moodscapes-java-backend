package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.ClientRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.ClientResponseDTO;

import java.util.List;

public interface IClientService {
    List<ClientResponseDTO> getAllClient();
    List<ClientResponseDTO> getAllClientsByUserId(String userId);

    ClientResponseDTO getClientById(String id);

    ClientResponseDTO addClient(ClientRequestDTO requestDTO);
//    ClientResponseDTO addClientFromEvent(ClientRequestDTO requestDTO, String eventId);

    ClientResponseDTO updateClient(String id, ClientRequestDTO updatedClient);

    String deleteClient(String id);

    String deleteListOfClients(List<String> ids);
}
