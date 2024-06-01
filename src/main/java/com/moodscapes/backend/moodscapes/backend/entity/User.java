package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
public class User extends Auditable{

    @Id
    @GeneratedValue(generator = "user-id")
    @GenericGenerator(name = "user-id", strategy = "com.moodscapes.backend.moodscapes.backend.util.CustomIdGenerator")
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    private String id;
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

}
