package com.moodscapes.backend.moodscapes.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event extends Auditable {
    private String eventId;
    private String userId;
    private String title;
//    private EventCategoryId eventCategoryId;
    private String location;
    private LocalDateTime eventDate;
    private List<String> sharedUserId;
    private int currencyId;
//    private Client clientId;
    private int guest;
    private boolean available;
    private String note;
}
