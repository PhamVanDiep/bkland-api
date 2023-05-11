package com.api.bkland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BKLandApplication {

	public static void main(String[] args) {
		SpringApplication.run(BKLandApplication.class, args);
	}

}
