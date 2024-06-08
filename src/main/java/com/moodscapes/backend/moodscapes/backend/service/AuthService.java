package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Override
    public void signInWithMagicLink(String email) {
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

    @Transactional
    protected abstract void authenticate(String token, HttpServletRequest request, HttpServletResponse response);
}
