package com.moodscapes.backend.moodscapes.backend.controller;

import com.moodscapes.backend.moodscapes.backend.domain.RequestContext;
import  com.moodscapes.backend.moodscapes.backend.dto.request.UserRequestDTO;
import com.moodscapes.backend.moodscapes.backend.dto.response.HttpResponse;
import com.moodscapes.backend.moodscapes.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.moodscapes.backend.moodscapes.backend.util.RequestUtils.getResponse;
import static java.util.Collections.emptyMap;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@Slf4j
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public String[] getUser() {
        String[] users = {"John Doe", "Jane Smith"};
        return users;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> createUSer(@RequestBody UserRequestDTO requestDTO, HttpServletRequest request){
        userService.createUser(requestDTO);
        return ResponseEntity
                .created(getUri())
                .body(getResponse(request, emptyMap(), "Account Created", CREATED));
    }

    private URI getUri(){
        return URI.create("");
    }

    @GetMapping("/list")
    public List<String> listUsers() {
        List<String> users = new ArrayList<>();
        users.add("John Doe");
        users.add("Jane Smith");
        return users;
    }
}
