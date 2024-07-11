package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.dto.response.ClientResponseDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.GuestResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClientRepo extends JpaRepository<Client, String> {
    List<ClientResponseDTO> findByUserId(String userId);

    @Query("SELECT e.id FROM Client e WHERE e.id IN :ids")
    List<String> findAllByIdIn(@Param("ids") List<String> ids);

    @Transactional
    void deleteByIdIn(List<String> ids);
}
