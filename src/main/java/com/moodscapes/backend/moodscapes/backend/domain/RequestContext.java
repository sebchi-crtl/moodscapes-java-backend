package com.moodscapes.backend.moodscapes.backend.domain;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Moodscapes
 * @version 1.0
 * @license Moodscapes, LLC (<a href="https://moodscapes.com>moodscapes</a>")
 * @email moodscapes@gmail.com
 * @since 05/12/24
 * @developer chiemelie nwobdodo, alvin dera
 */
@Slf4j
public class RequestContext {
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    public static void start(){
        log.info("user was removed {}" + USER_ID);
        USER_ID.remove();
    }

    public static void setUserId(String userId){
        USER_ID.set(userId);
    }

    public static String getUserId(){
        System.out.println(USER_ID.get());
        return USER_ID.get();
    }

}
