package com.moodscapes.backend.moodscapes.backend.dao;

import com.moodscapes.backend.moodscapes.backend.entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDetailRepo extends JpaRepository<BookingDetail, String> {
}
