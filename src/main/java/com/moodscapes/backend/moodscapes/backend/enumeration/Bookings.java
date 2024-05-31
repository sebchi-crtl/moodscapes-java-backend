package com.moodscapes.backend.moodscapes.backend.enumeration;

import com.moodscapes.backend.moodscapes.backend.entity.BookingDetail;
import com.moodscapes.backend.moodscapes.backend.entity.User;
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
public class Bookings {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @NotNull
    private User userId;
//    @ManyToMany
//    @JoinColumn(name = "recipient_id")
//    private User recipient;
    private Set<BookingStatus> confirm;
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetail> details;
    private LocalDate eventDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
