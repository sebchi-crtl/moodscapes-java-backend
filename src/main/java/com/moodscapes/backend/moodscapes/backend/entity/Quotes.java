package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Vector;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quotes")
@EqualsAndHashCode(callSuper = false)
@JsonInclude(NON_DEFAULT)
public class Quotes{

    @Id
    @GeneratedValue(generator = "quote-id")
    @GenericGenerator(name = "quote-id", strategy = "com.moodscapes.backend.moodscapes.backend.util.CustomIdGenerator")
    @Column(name = "quote_id", updatable = false, nullable = false)
    private String id;
    private String userID;
    private String eventID;
    private String senderUserID;
    private String quoteResponse;
//    private Vector<CollectionsID> items;
    private LocalDateTime createdAt;
}



