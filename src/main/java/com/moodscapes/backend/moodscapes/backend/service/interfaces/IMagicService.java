package com.moodscapes.backend.moodscapes.backend.service.interfaces;

import com.moodscapes.backend.moodscapes.backend.dao.AuthRepo;
import com.moodscapes.backend.moodscapes.backend.entity.Auth;
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

public interface IMagicService {

    public String token();

    public void issueToken(String username);


}
