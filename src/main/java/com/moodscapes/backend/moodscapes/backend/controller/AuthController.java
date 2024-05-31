package com.moodscapes.backend.moodscapes.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.moodscapes.backend.moodscapes.backend.constant.SecurityConstants.ROLE_PLANNER;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @GetMapping
    public ResponseEntity<Map<String, String>> test(){
        return ResponseEntity
                .ok()
                .body(Map.of(
                        "Testing", "up and running",
                        "Name", "chiemelie"
                ));
    }
}
