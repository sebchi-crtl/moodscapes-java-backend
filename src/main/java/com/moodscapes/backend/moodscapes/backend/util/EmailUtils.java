package com.moodscapes.backend.moodscapes.backend.util;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailUtils {

    public static String getTokenEmailMessage(String token, String email, String host){

        return "Hello " + email + ",\n\nYour token has be cretated. Please click on the link below to sign in your account.\n\n" +
                getTokenUrl(host, token) + "\n\nThe Support Team";
    }

    public static String getNewAccountEmailMessage(String token, String email, String host){

        return "Hello " + email + ",\n\nThank you for choosing us. Please contact the help line if you have any issue.\n\n" +
                "\n\nThe Support Team";
    }

    private static String getTokenUrl(String host, String token) {
        log.info(host + "/api/v1/auth/{}" + token);
        return host + "/api/v1/auth/{}" + token;
    }

}
