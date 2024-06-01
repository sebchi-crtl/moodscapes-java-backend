package com.moodscapes.backend.moodscapes.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableJpaRepositories("com.moodscapes.backend.moodscapes.backend.dao")
@EntityScan("com.moodscapes.backend.moodscapes.backend.entity")
@EnableScheduling
public class MoodscapesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoodscapesBackendApplication.class, args);
	}

}
