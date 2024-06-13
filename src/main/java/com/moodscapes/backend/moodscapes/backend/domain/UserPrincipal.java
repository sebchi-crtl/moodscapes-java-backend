package com.moodscapes.backend.moodscapes.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPrincipal {
    private String id;
    private String createdBy;
    private String updatedBy;
    private String userId;
    private String email;
    private String fullName;
    private String bio;
    private String avatarUrl;
    private String phoneNumber;
    private String imageUrl;
    private String role;
    private String address;
    private String updatedAt;
    private String createdAt;
    private String authorities;
    private boolean enabled;
}
