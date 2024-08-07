package com.moodscapes.backend.moodscapes.backend.util;

import com.moodscapes.backend.moodscapes.backend.entity.Event;
import com.moodscapes.backend.moodscapes.backend.entity.EventCategory;
import com.moodscapes.backend.moodscapes.backend.entity.User;
import com.moodscapes.backend.moodscapes.backend.enumeration.Role;
import com.moodscapes.backend.moodscapes.backend.repository.EventCategoryRepo;
import com.moodscapes.backend.moodscapes.backend.repository.EventRepo;
import com.moodscapes.backend.moodscapes.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Component
public class DataInitializer {
    @Autowired
    private EventRepo eventRepository;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventCategoryRepo eventCategoryRepository;

    @Bean
    public CommandLineRunner run() {
        String userId= UUID.randomUUID().toString();
        return args -> {
            User user = new User();
            user.setUserId(userId);
            user.setEmail("seb@gmail.com");
            user.setFullName("Amechi john");
            user.setBio("this is me");
            user.setPhoneNumber(Collections.singleton("09033058867"));
            user.setEnabled(true);
            user.setImageUrl("https://media.gq-magazine.co.uk/photos/645b5c3c8223a5c3801b8b26/16:9/w_1280,c_limit/100-best-games-hp-b.jpg");
            user.setRole(Collections.singleton(Role.PLANNER));
            user.setAddress("my address");
            var savedUser = userRepo.saveAndFlush(user);
            // Create and save categories
            EventCategory musicCategory = new EventCategory();
            musicCategory.setUserId(savedUser.getUserId());
            musicCategory.setName("Music");
            musicCategory.setDescription("this is for music");
            EventCategory savedMusicCategory = eventCategoryRepository.save(musicCategory);

            EventCategory sportsCategory = new EventCategory();
            sportsCategory.setUserId(savedUser.getUserId());
            sportsCategory.setName("Sports");
            sportsCategory.setDescription("this is for sports");
            EventCategory savedSportsCategory = eventCategoryRepository.save(sportsCategory);

            // Create and save events
            Event musicEvent = new Event();
            musicEvent.setUserId(savedUser.getUserId());
            musicEvent.setTitle("Rock Concert");
            musicEvent.setEventCategory(savedMusicCategory);
            musicEvent.setLocation("America");
            musicEvent.setEventDate(now());
            musicEvent.setCurrency("USD");
            musicEvent.setNotes("red and white");
            eventRepository.save(musicEvent);

            Event sportsEvent = new Event();
            sportsEvent.setUserId(savedUser.getUserId());
            sportsEvent.setTitle("Football Match");
            sportsEvent.setEventCategory(savedSportsCategory);
            sportsEvent.setLocation("Nigeria");
            sportsEvent.setEventDate(now().plusMinutes(40));
            sportsEvent.setCurrency("NGN");
            sportsEvent.setNotes("red and white");
            eventRepository.save(sportsEvent);

            // Print out saved data
            System.out.println("Event Categories:");
            eventCategoryRepository.findAll().forEach(category ->
                    System.out.println("Category ID: " + category.getId() + ", Name: " + category.getName())
            );

            System.out.println("Events:");
            eventRepository.findAll().forEach(event ->
                    System.out.println("Event ID: " + event.getId() + ", Name: " + event.getTitle() + ", Category ID: " + event.getEventCategory().getId())
            );
        };
    }
}
