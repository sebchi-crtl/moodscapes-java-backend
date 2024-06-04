package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.entity.User;

import java.util.Optional;

public interface IUserService {

    void createUser(UserRequestDTO request);

    boolean getUserByEmail(String email);
}
