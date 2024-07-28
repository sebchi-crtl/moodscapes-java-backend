package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.entity.NewUserCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewUserCheckRepo extends JpaRepository<NewUserCheck, String> {
    Optional<NewUserCheck> findByEmail(String email);

    void deleteByEmail(String email);
}
