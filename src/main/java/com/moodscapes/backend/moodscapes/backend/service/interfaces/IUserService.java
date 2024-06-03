package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;

public interface IUserService {

    void createUser(UserRequestDTO request);
}
