package com.moodscapes.backend.moodscapes.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.AlternativeJdkIdGenerator;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public abstract class Auditable {
    @Id
    @GeneratedValue(generator = "audit-id")
    @GenericGenerator(name = "audit-id", strategy = "com.moodscapes.backend.moodscapes.backend.util.CustomIdGenerator")
    @Column(name = "id", updatable = false)
    private String id;
    private String referenceId = new AlternativeJdkIdGenerator().generateId().toString();
    @NotNull
    private String createdBy;
    @NotNull
    private String updatedBy;
    @NotNull
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @CreatedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void beforePersist(){
        var userId = "c3641fa8-0c27-4262-80ac-0a23ad12dacf";
//        var userId = RequestContext.getUserId();
//        if (userId == null)
//            throw new ApiException("Cannot persist entity without user ID in Request Context for the thread");
        setCreatedAt(now());
        setCreatedBy(userId);
        setUpdatedBy(userId);
        setUpdatedAt(now());
    }

    @PreUpdate
    public void beforeUpdate(){
        var userId = "c3641fa8-0c27-4262-80ac-0a23ad12dacf";
//        var userId = RequestContext.getUserId();
//        if (userId == null)
//            throw new ApiException("Cannot persist entity without user ID in Request Context for the thread");
        setUpdatedAt(now());
        setUpdatedBy(userId);
    }
}
