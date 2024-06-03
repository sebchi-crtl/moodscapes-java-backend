package com.moodscapes.backend.moodscapes.backend.util;

import com.moodscapes.backend.moodscapes.backend.repository.AuthRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@Slf4j
public class TokenCleaner {

    private final AuthRepo auth;

    public TokenCleaner(AuthRepo auth) {
        this.auth = auth;
    }

    @Scheduled(cron = "0 */1 * * * *")
    void clean(){
        LocalDateTime threshold = LocalDateTime.now().minus(5, MINUTES);
        auth.deleteExpired(threshold);

        log.info("Tokens: {}", auth.count());
    }
}
