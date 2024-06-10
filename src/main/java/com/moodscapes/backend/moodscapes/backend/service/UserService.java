package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.repository.CredentialRepo;
import com.moodscapes.backend.moodscapes.backend.repository.UserRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.moodscapes.backend.moodscapes.backend.util.UserUtils.createUserEntity;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final CredentialRepo credentialRepo;
    private final ApplicationEventPublisher publisher;

    @Override
    public void createUser(UserRequestDTO request) {
        try{
            if (!getUserByEmail(request.email())){
                var user = userRepo.save(createNewUser(request.email(), request.fullName(), request.role()));
                String password = shuffleString(request.email());
                var credential = new Credential(user, password);
                credentialRepo.save(credential);
                publisher.publishEvent(new UserEvent( this, user, true));
            }
            else throw new ApiException("User Already exists");
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            throw new RequestValidationException("An error occurred. Please try again");
        }
    }

    public static String shuffleString(String input) {
        List<Character> characters = new ArrayList<>();
        for (char c : input.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder shuffledString = new StringBuilder();
        for (char c : characters) {
            shuffledString.append(c);
        }
        return shuffledString.toString();
    }

    private User createNewUser(String email, String fullName, Set<Role> role) {
        return createUserEntity(email, fullName, role);
    }

    @Override
    public boolean getUserByEmail(String email) {
        var existsByEmail = userRepo.existsByEmail(email);
        return existsByEmail;
    }

    @Override
    public User findUserByEmail(String email) {
        var existsByEmail = userRepo.findByEmail(email);
        return existsByEmail.orElseThrow(
                () -> new ApiException("user with email {} not found " + email)
        );
    }

}
