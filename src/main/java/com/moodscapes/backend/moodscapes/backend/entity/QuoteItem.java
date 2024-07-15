package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quotes")
@JsonInclude(NON_DEFAULT)
public class QuoteItem extends Auditable{
    private String vendorItemInfo;
    private Integer quantity;
    private Double unitPrice;
    private Double totalCost;
    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quotes quote;
}
