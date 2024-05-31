package com.moodscapes.backend.moodscapes.backend.dao;

import com.moodscapes.backend.moodscapes.backend.entity.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepo extends JpaRepository<Quotes, Long> {
}
