package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.AuthRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @GetMapping
    public ResponseEntity<Map<String, String>> test(){
        return ResponseEntity
                .ok()
                .body(Map.of(
                        "Testing", "up and running",
                        "Name", "chiemelie"
                ));
    }

    @PostMapping()
    public ResponseEntity<?> signInWithMagicLink(@RequestBody AuthRequestDTO request ){
        log.info(request.email());
        authService.signInWithMagicLink(request.email());
        return ResponseEntity
                .ok()
                .body( new HttpResponse(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        "create Token",
                        "token crated successfully",
                        "Chiemelie wrote this",
                        Map.of(
                                "email", request.email()
                        )

                ));
    }

    @PostMapping()
    public ResponseEntity<?> signInWithGoogleOAuth(@RequestBody AuthRequestDTO request ){
        log.info(request.email());
        authService.signInWithGoogleOAuth(request.email());
        return ResponseEntity
                .ok()
                .body( new HttpResponse(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        HttpStatus.OK,
                        "create Token",
                        "token crated successfully",
                        "Chiemelie wrote this",
                        Map.of(
                                "email", request.email()
                        )

                ));
    }
}
