package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.GuestRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Guest;

import java.util.List;

public interface IGuestService {
    List<GuestResponseDTO> getAllGuests();
    List<GuestResponseDTO> getAllGuestsByUserId(String userId);

    GuestResponseDTO getGuestById(String id);

    GuestResponseDTO addGuest(GuestRequestDTO guestRequestDTO);

    GuestResponseDTO updateGuest(String id, GuestRequestDTO updatedGuest);

    String deleteGuest(String id);
}
