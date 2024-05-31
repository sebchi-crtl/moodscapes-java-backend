package com.moodscapes.backend.moodscapes.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    @GetMapping()
    public String[] getUser() {
        String[] users = {"John Doe", "Jane Smith"};
        return users;
    }

    @GetMapping("/list")
    public List<String> listUsers() {
        List<String> users = new ArrayList<>();
        users.add("John Doe");
        users.add("Jane Smith");
        return users;
    }
}
