package com.moodscapes.backend.moodscapes.backend.service.interfaces;

public interface IAuthService {

    void signInWithMagicLink(String email);
    void signInWithGoogleOAuth(String email);
}
