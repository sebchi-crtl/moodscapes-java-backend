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
public class Guest extends Auditable{
    private String userId;
    private String eventId;
    private String firstName;
    private String lastName;
    private String grouping;
    private boolean rsvp;
    private String  mealPreference;
    private String notes;
}
