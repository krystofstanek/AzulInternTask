package com.krystofstanek.Azul.Intern.Task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * Main application class for the Azul Intern Task.
 * This class serves as the entry point for the Spring Boot application.
 */
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
@SpringBootApplication
public class AzulInternTaskApplication {

	/**
	 * Main method that launches the Spring Boot application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(AzulInternTaskApplication.class, args);
	}
}
