package com.moodscapes.backend.moodscapes.backend.dao;

import com.moodscapes.backend.moodscapes.backend.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends JpaRepository<Event, String> {
}
