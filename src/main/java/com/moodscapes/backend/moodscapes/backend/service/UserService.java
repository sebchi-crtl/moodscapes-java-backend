package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.repository.UserRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.moodscapes.backend.moodscapes.backend.constant.Constants.USER_BIO;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService {
    private UserRepo userRepo;

    @Override
    public void createUser(UserRequestDTO request) {
        try{
            if (!getUserByEmail(request.email())){
                var addUser = User
                        .builder()
                        .email(request.email())
                        .fullName(request.fullName())
                        .bio(USER_BIO)
                        .phoneNumber(request.phoneNumber())
                        .enabled(true)
                        .imageUrl(request.imageUrl())
                        .role(request.role())
                        .address(request.address())
                        .build();
                userRepo.save(addUser);
            }
            else throw new ApiException("User Already exists");

        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            throw new RequestValidationException("An error occurred. Please try again");
        }
    }

    @Override
    public boolean getUserByEmail(String email) {
        boolean existsByEmail = userRepo.existsByEmail(email);
        return existsByEmail;
    }
}
