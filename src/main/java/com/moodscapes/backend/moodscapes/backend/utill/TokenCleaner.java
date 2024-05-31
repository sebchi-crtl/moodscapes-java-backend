package com.moodscapes.backend.moodscapes.backend.utill;

import com.moodscapes.backend.moodscapes.backend.dao.AuthRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.time.temporal.ChronoUnit.MINUTES;

@Component
@AllArgsConstructor
@Slf4j
public class TokenCleaner {

    private final AuthRepo auth;

    @Scheduled(cron = "0 */1 * * * *")
    void clean(){
        LocalDateTime threshold = LocalDateTime.now().minus(5, MINUTES);
        auth.deleteExpired(threshold);

        log.info("Tokens: {}", auth.count());
    }
}
