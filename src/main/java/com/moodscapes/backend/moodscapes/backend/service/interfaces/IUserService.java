package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.entity.User;

import java.util.Optional;

public interface IUserService {
    void createUser(UserRequestDTO request);
    boolean getUserByEmail(String email);
    boolean getUserById(String email);
    User findUserByEmail(String email);
    Optional<User> getaUserByEmail(String email);
    UserPrincipal getUserPrincipalByEmail(String email);
    Credential getUserCredentialById(String id);
    UserPrincipal findUserPrincipalByEmail(String email);
}
