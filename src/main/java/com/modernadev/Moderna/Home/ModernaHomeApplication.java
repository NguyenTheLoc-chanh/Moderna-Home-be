package com.modernadev.Moderna.Home;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class ModernaHomeApplication {

	public static void main(String[] args) {
		// Load biến từ file .env
		Dotenv dotenv = Dotenv.load();
		System.setProperty("AWS_S3_ACCESS", Objects.requireNonNull(dotenv.get("AWS_S3_ACCESS")));
		System.setProperty("AWS_S3_SECRET", Objects.requireNonNull(dotenv.get("AWS_S3_SECRET")));

		SpringApplication.run(ModernaHomeApplication.class, args);
	}

}
