package com.clotzer.property;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot Property Loader Application.
 *
 * <p>This is the main entry point for a high-performance Spring Boot application that loads
 * property data from JSON files into a MySQL database using virtual threads for concurrent processing.
 *
 * <p>Key features include:
 * <ul>
 *   <li>Bulk data loading from JSON files</li>
 *   <li>Multi-threaded processing using Java virtual threads</li>
 *   <li>Configurable concurrency settings</li>
 *   <li>Robust error handling and data validation</li>
 *   <li>REST API for data access</li>
 *   <li>Performance monitoring and reporting</li>
 * </ul>
 *
 * <p>The application automatically loads property data on startup from
 * {@code src/main/resources/propertyFiles.json} and provides REST endpoints
 * for querying the loaded data.
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class PropertyApplication {

	/**
	 * Main method to start the Spring Boot application.
	 *
	 * <p>This method bootstraps the Spring application context and starts
	 * the embedded web server. The application will automatically execute
	 * the data loading process through the {@link DataLoader} component.
	 *
	 * @param args command line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(PropertyApplication.class, args);
	}
}
