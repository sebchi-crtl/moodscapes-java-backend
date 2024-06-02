package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "client")
@Entity
@JsonInclude(NON_DEFAULT)
public class Client extends Auditable{
    private String userID;
    private String firstName;
    private String lastName;
    private String country;
    private String phoneNumber;
    @Email
    private String  email;
    private float budget;
    private boolean active;
    private String notes;

}
