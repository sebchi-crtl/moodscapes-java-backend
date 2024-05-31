package com.moodscapes.backend.moodscapes.backend.constant;

public class SecurityConstants {

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORITY_DELIMITER = ",";
    public static final String ROLE_SUPAADMIN = "SUPAADMIN";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_PLANNER = "PLANNER";
    public static final String ROLE_VENDOR = "VENDOR";
    public static final String ROLE_CENTER = "CENTER";


    public static final String PLANNER_AUTHORITIES = "event:create, event:update, event:delete";
    public static final String ADMIN_AUTHORITIES = "user:read, user:write, event:create, event:update";
    public static final String SUPAADMIN_AUTHORITIES = "user:read, user:write, user:delete, event:create, event:update, event:delete";
    public static final String VENDOR_AUTHORITIES = "vendor:read, vendor:write";
    public static final String CENTER_AUTHORITIES = "center:read, center:write";
}
