package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Repository
public interface AuthRepo extends JpaRepository<Auth, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Auth a WHERE a.createdAt < :threshold")
    void deleteExpired(LocalDateTime threshold);
    Auth findByToken(String token);
}
