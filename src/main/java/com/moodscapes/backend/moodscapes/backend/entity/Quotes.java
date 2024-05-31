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
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Vector;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quotes {

    @Id
    @GeneratedValue(generator = "custom-id")
    @GenericGenerator(name = "custom-id", strategy = "com.moodscapes.backend.moodscapes.backend.utill.CustomIdGenerator")
    private Long quoteId;
    private String userID;
    private String eventID;
    private String senderUserID;
    private String quoteResponse;
//    private Vector<CollectionsID> items;
    private LocalDateTime createdAt;
}
