package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.dto.response.EventCategoryResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCategoryRepo extends JpaRepository<EventCategory, String> {
    List<EventCategoryResponseDTO> findByUserId(String userId);

//    List<EventResponseDTO> findByEventCategory(String category);
}
