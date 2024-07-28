package com.moodscapes.backend.moodscapes.backend.constant;

public class SecurityConstants {

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String LOGIN_PATH = "/api/v1/login";
    public static final String ROLE = "role";
    public static final String EMPTY_VALUE = "empty";
    public static final String JWT_TYPE = "JWT";
    public static final String TYPE = "typ";
    public static final String GET_MOODSCAPES_LLC = "GET_MOODSCAPES_LLC";
    public static final String AUTHORITIES = "authorities";
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
    private static final int STRENGTH = 12;

    public static int getStrength(){
        return STRENGTH;
    }

}
