package com.moodscapes.backend.moodscapes.backend.entity;

import com.moodscapes.backend.moodscapes.backend.entity.BookingDetail;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.BookingStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bookings extends Auditable {

    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User senderId;
    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    private User recipientId;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<BookingStatus> confirm;
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetail> details;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
