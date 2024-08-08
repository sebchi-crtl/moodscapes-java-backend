package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.dto.request.AuthRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.exception.ApiException;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IAuthService;
import com.moodscapes.backend.moodscapes.backend.service.interfaces.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Slf4j
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final IAuthService authService;
    private final IUserService userService;

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
    public ResponseEntity<?> signInWithMagicLink(@RequestBody AuthRequestDTO requestDTO, HttpServletRequest request){
        try {
            log.info(requestDTO.email());
            authService.sendMagicLinkToken(requestDTO.email());
            return ResponseEntity
                    .ok()
                    .body( getResponse(request, Map.of(
                            "email", requestDTO.email(),
                            "user", "token sent"
                    ), "magic link token sent", OK));
        }
        catch (Exception ex) {
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }

    @PostMapping("/google")
    public ResponseEntity<HttpResponse> signInWithGoogleOAuth(@RequestBody AuthRequestDTO requestDTO, HttpServletRequest request ){
        log.info(requestDTO.email());
        authService.signInWithGoogleOAuth(requestDTO.email());
        return ResponseEntity
                .ok()
                .body( getResponse(request, Map.of(
                        "email", requestDTO.email(),
                        "user", "create user"
                ), "Account Ready", OK));
    }

    @GetMapping("/magic_link/{token}")
    public ResponseEntity<HttpResponse> authenticate(@PathVariable String token, HttpServletRequest request){
        try {
            log.info(token);
            authService.signInWithMagicLink(token, request);
            return ResponseEntity
                    .ok()
                    .body(getResponse(request, Map.of(
                            "token", token,
                            "user", "create user"
                    ), "Account Ready", OK));
        }
        catch (Exception ex){
            log.error("Error getting message: {}", ex.getMessage());
            throw new ApiException(ex.getMessage());
        }
    }
}
