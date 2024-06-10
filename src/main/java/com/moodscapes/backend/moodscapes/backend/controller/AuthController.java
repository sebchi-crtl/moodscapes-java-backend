package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.AuthRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;

import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @PostMapping("/magic_link")
    public ResponseEntity<?> signInWithMagicLink(@RequestBody AuthRequestDTO request ){
        log.info(request.email());
        authService.sendMagicLinkToken(request.email());
        return ResponseEntity
                .ok()
                .body( new HttpResponse(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "this is the path",
                        HttpStatus.OK,
                        "create Token",
                        "token crated successfully",
                        "Chiemelie wrote this",
                        Map.of(
                                "email", request.email()
                        )

                ));
    }

    @PostMapping("/google")
    public ResponseEntity<HttpResponse> signInWithGoogleOAuth(@RequestBody AuthRequestDTO request ){
        log.info(request.email());
        authService.signInWithGoogleOAuth(request.email());
        return ResponseEntity
                .ok()
                .body( new HttpResponse(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "this is the path",
                        HttpStatus.OK,
                        "create Token",
                        "token crated successfully",
                        "Chiemelie wrote this",
                        Map.of(
                                "email", request.email()
                        )

                ));
    }

    @GetMapping("/magic_link/{token}")
    public ResponseEntity<HttpResponse> authenticate(@PathVariable String token, HttpServletRequest request, @RequestBody UserRequestDTO userRequestDTO){
        try {
            log.info(token);
            authService.signInWithMagicLink(token, userRequestDTO);
            log.info("this is your user request " + userRequestDTO);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "token", token
                    ), "Account Ready", OK));
        }
        catch (Exception ex){
            throw new ApiException(ex.getMessage());
        }
    }
}
