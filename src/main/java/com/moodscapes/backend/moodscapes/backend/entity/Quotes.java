package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moodscapes.backend.moodscapes.backend.enumeration.QuoteStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    @NotNull
    private String receiverUserId;
    private String eventName;
    private Double totalCost;
    private QuoteStatus status;
    private String eventMessage;
    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuoteItem> items;
//    private Vector<CollectionsID> items;
}



