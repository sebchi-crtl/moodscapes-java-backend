package com.moodscapes.backend.moodscapes.backend.dao;

import com.moodscapes.backend.moodscapes.backend.enumeration.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepo extends JpaRepository<Bookings, Long> {
}
