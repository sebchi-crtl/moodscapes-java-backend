package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "newUserCheck")
@Entity
public class NewUserCheck extends Auditable {
    private boolean newUser;
    @Column(unique = true)
    private String email;

}
