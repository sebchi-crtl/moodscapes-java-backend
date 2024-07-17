package com.moodscapes.backend.moodscapes.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDetail extends Auditable{
    @ManyToOne
    private Booking booking;
    private String item;
    private String  venue;
    private int quantity; // For items, e.g., number of chairs

}
