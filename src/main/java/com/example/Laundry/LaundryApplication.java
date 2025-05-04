package com.example.Laundry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.example.Laundry.domain")
@EnableJpaRepositories("com.example.Laundry.repository")
@EnableJpaAuditing
public class LaundryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaundryApplication.class, args);
	}

}
