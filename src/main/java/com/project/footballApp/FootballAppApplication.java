package com.project.footballApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FootballAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FootballAppApplication.class, args);
	}

}
