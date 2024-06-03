package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.service.interfaces.IAuthService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;

public abstract class AuthService implements IAuthService {
    @Override
    public void signInWithMagicLink(String email) {
        try {
            System.out.println("memem");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void signInWithGoogleOAuth(String email) {
        try {
            System.out.println("memem");
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());

        }
    }
}
