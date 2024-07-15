package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moodscapes.backend.moodscapes.backend.enumeration.QuoteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quotes")
@JsonInclude(NON_DEFAULT)
public class Quotes extends Auditable{
    private String userId;
    private String vendorName;
    private String receiverUserId;
    private String senderUserID;
    private Double totalCost;
    private QuoteStatus status;
    private String eventName;
    private String eventMessage;
    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuoteItem> items;
//    private Vector<CollectionsID> items;
}



