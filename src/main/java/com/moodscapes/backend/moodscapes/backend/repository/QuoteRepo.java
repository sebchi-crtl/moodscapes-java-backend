package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.dto.response.QuoteResponseDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Quotes;
import org.springframework.beans.PropertyValues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepo extends JpaRepository<Quotes, String> {
    List<Quotes> findByUserId(String userId);
}
