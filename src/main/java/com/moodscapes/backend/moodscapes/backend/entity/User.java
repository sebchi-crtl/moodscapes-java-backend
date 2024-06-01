package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
@JsonInclude(NON_DEFAULT)
public class User extends Auditable{


    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private Long id;
    @Email(message = "invalid email. Please provide a valid email")
    @Column(nullable = false, unique = true)
    private String email;
    private String fullName;
    private String bio;
    @ElementCollection
    private Set<String> phoneNumber;
    private boolean enabled;
    private String imageUrl;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> role;
    private String address;
    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}
