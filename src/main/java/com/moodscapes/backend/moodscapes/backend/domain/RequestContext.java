package com.moodscapes.backend.moodscapes.backend.domain;

/**
 * @author Moodscapes
 * @version 1.0
 * @license Moodscapes, LLC (<a href="https://moodscapes.com>moodscapes</a>")
 * @email moodscapes@gmail.com
 * @since 05/12/24
 * @developer chiemelie nwobdodo, alvin dera
 */
public class RequestContext {

    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();

    private RequestContext(){}

    public static void start(){USER_ID.remove();}

    public static void setUserId(Long userId){
        USER_ID.set(userId);
    }

    public static Long getUserId(){return USER_ID.get();}

}
