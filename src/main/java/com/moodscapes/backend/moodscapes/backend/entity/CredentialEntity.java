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
@Table(name = "users")
@Entity
@JsonInclude(NON_DEFAULT)
public class CredentialEntity extends Auditable {
    private String password;
    @OneToOne(targetEntity = Auth.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("auth_id")
    private Auth userAuth;

    public CredentialEntity(Auth userAuth, String password) {
        this.userAuth = userAuth;
        this.password = password;
    }
}
