package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.entity.QuoteItem;
import com.moodscapes.backend.moodscapes.backend.entity.Quotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteItemRepo extends JpaRepository<QuoteItem, String> {
}
