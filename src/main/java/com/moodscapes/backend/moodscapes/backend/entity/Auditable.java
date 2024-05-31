package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.*;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class Auditable {
    @Id
    @SequenceGenerator(name = "primary_key_seq", sequenceName = "primary_key_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "primary_key_seq")
    @Column(name = "id", updatable = false)
    private Long id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private Long createdBy;
    @NotNull
    private Long updatedBy;
    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist(){
        var userId = RequestContext.getUserId();
        if (userId == null)
            throw new ApiException("Cannot persist entity without user ID in Request Context for the thread");
        setCreatedAt(now());
        setCreatedBy(userId);
        setUpdatedBy(userId);
        setUpdatedAt(now());
    }

    @PreUpdate
    public void beforeUpdate(){
        var userId = RequestContext.getUserId();
        if (userId == null)
            throw new ApiException("Cannot update entity without user ID in Request Context for the thread");
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }
}
