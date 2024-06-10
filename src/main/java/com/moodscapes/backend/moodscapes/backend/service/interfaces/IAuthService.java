package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

public interface IAuthService {

    void sendMagicLinkToken(String email);

    void signInWithMagicLink(String token, UserRequestDTO userRequestDTO);

    void signInWithGoogleOAuth(String email);
//    @Transactional
//    void authenticate(String email, HttpServletRequest request, HttpServletResponse response);
}
