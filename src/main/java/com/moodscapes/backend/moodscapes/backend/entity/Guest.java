package com.moodscapes.backend.moodscapes.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Guest extends Auditable{

    private Long guestId;
    private String userID;
    private String eventID;
    private String firstName;
    private String lastName;
    private String grouping;
    private boolean RSVP;
    private String  mealPreference;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
