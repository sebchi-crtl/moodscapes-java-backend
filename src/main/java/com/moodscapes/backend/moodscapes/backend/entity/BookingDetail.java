package com.moodscapes.backend.moodscapes.backend.entity;

import com.moodscapes.backend.moodscapes.backend.enumeration.BookingItemType;
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
    private String itemId;
    private String itemName;
    private String vendorName;
    private String imageUrl;
    private Integer quantity;
    private Double unitPrice;
    private Double totalCost;
    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

}
