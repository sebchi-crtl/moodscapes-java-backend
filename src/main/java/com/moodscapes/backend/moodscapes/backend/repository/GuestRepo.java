package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Guest;
import io.micrometer.common.KeyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepo extends JpaRepository<Guest, String> {
    List<GuestResponseDTO> findByUserId(String userId);

}
