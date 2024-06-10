package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moodscapes.backend.moodscapes.backend.enumeration.SignInMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth")
@JsonInclude(NON_DEFAULT)
public class Auth{
    @Id
    @GeneratedValue(generator = "auth-id")
    @GenericGenerator(name = "auth-id", strategy = "com.moodscapes.backend.moodscapes.backend.util.CustomIdGenerator")
    @Column(name = "auth_id", updatable = false)
    private String id;
    @Email(message = "invalid email. Please provide a valid email")
    @Column(nullable = false, unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SignInMethod methodProvider;
    private LocalDateTime lastLogin;
    private boolean isNotLocked;
//    @Transient
    private String token;
    private LocalDateTime createdAt;

}
