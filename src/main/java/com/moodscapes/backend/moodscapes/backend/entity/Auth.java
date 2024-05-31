package com.moodscapes.backend.moodscapes.backend.entity;

import com.moodscapes.backend.moodscapes.backend.enumeration.SignInMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth")
public class Auth extends Auditable{
    @Id
    @GeneratedValue(generator = "auth-id")
    @GenericGenerator(name = "auth-id", strategy = "com.moodscapes.backend.moodscapes.backend.utill.CustomIdGenerator")
    private String authId;
    @Email(message = "invalid email. Please provide a valid email")
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignInMethod methodProvider;
    private Integer loginAttempts;
    private LocalDateTime lastLogin;
    private boolean isNotLocked;
    @Transient
    private String token;
    private LocalDateTime createdAt;

}
