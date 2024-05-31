package com.moodscapes.backend.moodscapes.backend.entity;

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.PLANNER_AUTHORITIES;

public enum Role {
    SUPAADMIN(
            "ROLE_SUPAADMIN",
            "This role as all access privilege",
            "user:read", "user:write", "user:delete", "event:create", "event:update", "event:delete"
    ),
    ADMIN(
            "ROLE_ADMIN",
            "This role as all access read and write privilege",
            "user:read", "user:write", "event:create", "event:update"
    ),
    PLANNER("ROLE_PLANNER",
            "This role as all planner access privilege",
            PLANNER_AUTHORITIES
    ),
    VENDOR("ROLE_VENDOR",
            "This role as all vendor access privilege",
            "vendor:read", "vendor:write"
    ),
    CENTER("ROLE_CENTER",
            "This role as all center access privilege",
            "center:read", "center:write"
    );

    private final String roleName;
    private final String description;
    private final String[] authorities;

    Role(String roleName, String description, String... authorities) {
        this.roleName = roleName;
        this.description = description;
        this.authorities = authorities;
    }

    public String getRoleName() {
        return roleName;
    }
    public String getDescription() {
        return description;
    }

    public String[] getAuthorities() {
        return authorities;
    }
}
