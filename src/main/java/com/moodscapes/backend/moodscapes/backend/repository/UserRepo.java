package com.moodscapes.backend.moodscapes.backend.repository;

import com.moodscapes.backend.moodscapes.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    User findAByEmail(String email);

    boolean existsByEmail(String email);
}
