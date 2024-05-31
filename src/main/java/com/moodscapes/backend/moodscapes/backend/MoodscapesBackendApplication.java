package com.moodscapes.backend.moodscapes.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MoodscapesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodscapesBackendApplication.class, args);
	}

}
