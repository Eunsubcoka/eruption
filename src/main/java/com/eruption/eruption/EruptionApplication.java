package com.eruption.eruption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EruptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EruptionApplication.class, args);
	}

}
