package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
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
@Builder
@JsonInclude(NON_DEFAULT)
public class User extends Auditable{
    @Column(name = "userId", updatable = false, unique = true, nullable = false)
    private String userId;
    @Email(message = "invalid email. Please provide a valid email")
    @Column(nullable = false, unique = true)
    private String email;
    private String fullName;
    private String bio;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<String> phoneNumber;
    private boolean enabled;
    private String imageUrl;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> role;
    private String address;

}
