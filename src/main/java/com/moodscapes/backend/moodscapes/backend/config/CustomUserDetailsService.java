package com.moodscapes.backend.moodscapes.backend.config;

import com.moodscapes.backend.moodscapes.backend.repository.UserRepo;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsService  {


//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        log.info("check for user email " + email);
//
//        User user = userRepo.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with email: " + email);
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(), "hhhwwbdh", new ArrayList<>());
//
//    }
}
