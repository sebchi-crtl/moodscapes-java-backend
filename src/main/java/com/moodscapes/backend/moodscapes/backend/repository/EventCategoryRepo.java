package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Event;
import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCategoryRepo extends JpaRepository<EventCategory, String> {
    List<EventResponseDTO> findByUserId(String userId);

//    List<EventResponseDTO> findByEventCategory(String category);
}
