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
public class BookingDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingDetailId;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Bookings booking;

//    @ManyToOne
//    @JoinColumn(name = "item_id", nullable = true)
//    private Item item;

//    @ManyToOne
//    @JoinColumn(name = "venue_id", nullable = true)
//    private Venue venue;

    private int quantity; // For items, e.g., number of chairs

}
