package com.moodscapes.backend.moodscapes.backend.enumeration;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.*;

public enum Role {
    SUPAADMIN(
            "ROLE_SUPAADMIN",
            "This role as all access privilege",
            SUPAADMIN_AUTHORITIES
    ),
    ADMIN(
            "ROLE_ADMIN",
            "This role as all access read and write privilege",
            ADMIN_AUTHORITIES
    ),
    PLANNER("ROLE_PLANNER",
            "This role as all planner access privilege",
            PLANNER_AUTHORITIES
    ),
    VENDOR("ROLE_VENDOR",
            "This role as all vendor access privilege",
            VENDOR_AUTHORITIES
    ),
    CENTER("ROLE_CENTER",
            "This role as all center access privilege",
            CENTER_AUTHORITIES
    );

    private final String roleName;
    private final String description;
    private final String authorities;

    Role(String roleName, String description, String authorities) {
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
    }
}
