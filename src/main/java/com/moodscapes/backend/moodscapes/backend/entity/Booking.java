package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingItemType;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Booking extends Auditable {

    @NotNull
    private String userId;
    @NotNull
    private String recipientUserId;
    @ManyToOne
    @JoinColumn(name = "eventId")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("id")
    private Event event;
    private String plannerName;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private BookingStatus confirm;
    private Double totalCost;
    @Enumerated(EnumType.STRING)
    private BookingItemType itemType;
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetail> details;
}
