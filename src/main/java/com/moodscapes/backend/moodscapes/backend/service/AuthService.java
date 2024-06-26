package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IAuthService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public abstract class AuthService implements IAuthService {

    private static final SecureRandom random = new SecureRandom();
    private static final List<Character> alphabet =
            IntStream.concat(
                            IntStream.rangeClosed('a', 'z'),
                            IntStream.concat(
                                    IntStream.rangeClosed('A', 'Z'),
                                    IntStream.rangeClosed('0', '9')
                            )
                    )
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.toList());


    protected static String token(int size){
        return IntStream
                .range(0, size)
                .map(i -> random.nextInt(alphabet.size()))
                .mapToObj(alphabet::get)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public abstract void sendAuthentication(String email);

    protected Auth saveToken(String email, String token) {
        return null;
    }

    @Transactional
    protected abstract void authenticate(String token, UserRequestDTO userRequestDTO);


    @Override
    public void signInWithMagicLink(String token, UserRequestDTO userRequestDTO) {
        try {
            System.out.println("did the token come in here? " + token);
            log.info("did your user request reach here? " + userRequestDTO);
            authenticate(token, userRequestDTO);
        }
        catch(Exception ex){
            throw new ApiException(ex.getMessage());
        }

    }

    @Override
    public void sendMagicLinkToken(String email) {
        try {
            System.out.println("memem");
            sendAuthentication(email);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void signInWithGoogleOAuth(String email) {
        try {
            System.out.println("this is the email sent :" + email);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());

        }
    }

}
