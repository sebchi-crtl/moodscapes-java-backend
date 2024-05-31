package com.moodscapes.backend.moodscapes.backend.service;

import com.moodscapes.backend.moodscapes.backend.dao.AuthRepo;
import com.moodscapes.backend.moodscapes.backend.entity.Auth;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IMagicService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.moodscapes.backend.moodscapes.backend.enumeration.SignInMethod.MagicLink;

@Service
@RequiredArgsConstructor
@Slf4j
public class MagicService implements IMagicService {

    private final UserDetailsService users;
    private final AuthRepo auth;
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


    private static String token(int size){
        return IntStream
                .range(0, size)
                .map(i -> random.nextInt(alphabet.size()))
                .mapToObj(alphabet::get)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    public String token() {
        return token(64);
    }

    @Override
    public void issueToken(String username){
//        Todo: import this to auth service with exceptions and try catch
        var user = users.loadUserByUsername(username);
        var token = token();
        Auth magic = auth.save(
                Auth
                        .builder()
                        .email(user.getUsername())
                        .token(token)
                        .methodProvider(MagicLink)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        mail(magic);
    }

    private void mail(@NotNull Auth token) {
        log.info("{} has link http://localhost:8090/api/v1/auth/{}", token.getEmail(), token.getToken());
    }

}
