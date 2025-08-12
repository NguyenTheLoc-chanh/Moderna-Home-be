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
		System.setProperty("CLOUD_NAME", Objects.requireNonNull(dotenv.get("CLOUD_NAME")));
		System.setProperty("CLOUD_API_KEY", Objects.requireNonNull(dotenv.get("CLOUD_API_KEY")));
		System.setProperty("CLOUD_API_SECRET", Objects.requireNonNull(dotenv.get("CLOUD_API_SECRET")));

		SpringApplication.run(ModernaHomeApplication.class, args);
	}

}
