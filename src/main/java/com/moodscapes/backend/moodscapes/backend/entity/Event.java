package com.moodscapes.backend.moodscapes.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event extends Auditable {
    private String userId;
    private String title;
    @ManyToOne
    @JoinColumn(name = "event_category", referencedColumnName = "id")
    private EventCategory eventCategory;
    private String location;
    private LocalDateTime eventDate;
    private String currency;
    private String notes;
    private List<String> sharedUserId;
}
