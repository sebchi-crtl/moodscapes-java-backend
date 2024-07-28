package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.domain.UserPrincipal;
import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Credential;
import com.moodscapes.backend.moodscapes.backend.entity.NewUserCheck;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import com.moodscapes.backend.moodscapes.backend.enumeration.converter.RoleConverter;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.exception.RequestValidationException;
import com.moodscapes.backend.moodscapes.backend.repository.CredentialRepo;
import com.moodscapes.backend.moodscapes.backend.repository.NewUserCheckRepo;
import com.moodscapes.backend.moodscapes.backend.repository.UserRepo;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.moodscapes.backend.moodscapes.backend.util.UserUtils.createUserEntity;
import static com.moodscapes.backend.moodscapes.backend.util.UserUtils.fromUserPrincipal;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class UserService implements IUserService {
    private final UserRepo userRepo;
    private final CredentialRepo credentialRepo;
    private final ApplicationEventPublisher publisher;
    private final NewUserCheckRepo newUserCheckRepo;
    private final MagicService magicService;
    private final RoleConverter roleConverter;

    @Override
    public void createUser(UserRequestDTO request) {
        try{
            String email = request.email();
            if (!getUserByEmail(email)){
                if (findNewUserByEmail(email).isNewUser()){
                    var user = userRepo.save(createNewUser(email, request.fullName(), request.role()));
                    String password = shuffleString(email);
                    var credential = new Credential(user, password);
                    credentialRepo.save(credential);
                    publisher.publishEvent(new UserEvent( this, user, true));
                    newUserCheckRepo.deleteByEmail(email);
                    //TODO: SIGN-IN METHOD
                    magicService.signingInUser(email);
                }
                else {
                    throw new ApiException("you are not allowed to create a user");
                }

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
    private NewUserCheck findNewUserByEmail(String email){
        return newUserCheckRepo.findByEmail(email).orElse(null);
    }

    @Override
    public boolean getUserByEmail(String email) {
        var existsByEmail = userRepo.existsByEmail(email);
        return existsByEmail;
    }

    @Override
    public boolean getUserById(String email) {
        var existsById = userRepo.existsById(email);
        return existsById;
    }

    @Override
    public User findUserByEmail(String email) {
        var existsByEmail = userRepo.findByEmail(email);
        return existsByEmail.orElseThrow(
                () -> new ApiException("user with email {} not found " + email)
        );
    }

    @Override
    public Optional<User> getaUserByEmail(String email) {
        User byEmail = userRepo.findAByEmail(email);
        return Optional.ofNullable(byEmail);
    }

    @Override
    public UserPrincipal getUserPrincipalByEmail(String email) {
        var byEmail = userRepo.findUserPrincipalByEmail(email);
        return byEmail.orElse(null);
    }

    @Override
    public Credential getUserCredentialById(String id) {
        var credentialId = credentialRepo.getCredentialByUserAuth_UserId(id);
        return credentialId.orElseThrow(() -> new ApiException("unable to find user credential"));
    }
    @Override
    public UserPrincipal findUserPrincipalByEmail(String email) {
        User user = findUserByEmail(email);
        return fromUser(user, Role.valueOf(user.getRole().toString()), getUserCredentialById(user.getId()));
    }

    private UserPrincipal fromUser(User user, Role role, Credential userCredentialById) {
//        UserUtils userUtils = new UserUtils();
        return fromUserPrincipal(user, roleConverter.convertToEntityAttribute(role.name()), userCredentialById);
    }


}
