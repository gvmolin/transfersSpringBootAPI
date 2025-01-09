package com.example.transfersApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TransfersApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransfersApiApplication.class, args);
	}

}
