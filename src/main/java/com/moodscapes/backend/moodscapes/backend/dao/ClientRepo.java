package com.moodscapes.backend.moodscapes.backend.dao;

import com.moodscapes.backend.moodscapes.backend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends JpaRepository<Client, String> {
}
