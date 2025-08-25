package com.clotzer.property;

import com.clotzer.property.entity.Property;
import com.clotzer.property.repository.PropertyRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Data loader component responsible for loading property data from JSON files into the database.
 *
 * <p>This component implements {@link CommandLineRunner} to execute automatically on application startup.
 * It parses JSON property data from {@code /propertyFiles.json} in the classpath and saves it to the
 * MySQL database using batch operations for optimal performance.
 *
 * <p>Key features:
 * <ul>
 *   <li>Automatic execution on application startup</li>
 *   <li>JSON parsing and validation with detailed error reporting</li>
 *   <li>Configurable concurrent processing via application properties</li>
 *   <li>Robust error handling with graceful failure recovery</li>
 *   <li>Performance monitoring and timing reports</li>
 *   <li>Debug logging for the first few processed properties</li>
 * </ul>
 *
 * <p>Configuration properties:
 * <ul>
 *   <li>{@code property.loader.concurrent-threads} - Number of concurrent threads (default: 10)</li>
 *   <li>{@code property.loader.enabled} - Enable/disable the loader (default: true)</li>
 * </ul>
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 * @see Property
 * @see PropertyRepository
 * @see com.clotzer.property.PropertyService
 */
@Component
public class DataLoader implements CommandLineRunner {

    /** Repository for property database operations */
    private final PropertyRepository propertyRepository;

    /** Jackson ObjectMapper for JSON parsing */
    private final ObjectMapper objectMapper;

    /** Service layer for transactional property operations */
    private final com.clotzer.property.PropertyService propertyService;

    /** Number of concurrent threads for property processing (configurable via properties) */
    @Value("${property.loader.concurrent-threads:10}")
    private int concurrentThreads;

    /** Flag to enable/disable the property loader (configurable via properties) */
    @Value("${property.loader.enabled:true}")
    private boolean loaderEnabled;

    /**
     * Constructs a new DataLoader with required dependencies.
     *
     * @param propertyRepository the repository for property database operations
     * @param objectMapper the Jackson ObjectMapper for JSON parsing
     * @param propertyService the service layer for transactional operations
     */
    public DataLoader(PropertyRepository propertyRepository, ObjectMapper objectMapper, com.clotzer.property.PropertyService propertyService) {
        this.propertyRepository = propertyRepository;
        this.objectMapper = objectMapper;
        this.propertyService = propertyService;
    }

    /**
     * Executes the property data loading process on application startup.
     *
     * <p>This method is automatically called by Spring Boot after the application context is loaded.
     * It performs the following operations:
     * <ol>
     *   <li>Checks if the loader is enabled via configuration</li>
     *   <li>Locates and validates the JSON file in the classpath</li>
     *   <li>Parses the JSON structure and validates the format</li>
     *   <li>Converts JSON objects to Property entities</li>
     *   <li>Saves properties to the database using batch operations</li>
     *   <li>Reports performance metrics and completion status</li>
     * </ol>
     *
     * <p>Error handling:
     * <ul>
     *   <li>Missing JSON file - logs error and returns gracefully</li>
     *   <li>Invalid JSON structure - logs detailed error information</li>
     *   <li>Property parsing errors - logs up to 5 errors with stack traces</li>
     *   <li>Database errors - logs full exception details</li>
     * </ul>
     *
     * @param args command line arguments (not used in this implementation)
     */
    @Override
    public void run(String... args) {
        if (!loaderEnabled) {
            System.out.println("Property loader is disabled");
            return;
        }

        System.out.println("Starting property data loading...");
        
        long start = System.currentTimeMillis();
        try {
            // First, let's check if the resource file exists
            InputStream inputStream = DataLoader.class.getResourceAsStream("/propertyFiles.json");
            if (inputStream == null) {
                System.err.println("ERROR: Could not find /propertyFiles.json in classpath");
                System.err.println("Make sure the file is in src/main/resources/");
                return;
            }
            
            System.out.println("Found propertyFiles.json, parsing...");
            
            JsonNode root = objectMapper.readTree(inputStream);
            System.out.println("Parsed JSON root: " + (root != null ? "SUCCESS" : "FAILED"));
            
            JsonNode propertiesNode = root.get("properties");
            if (propertiesNode == null) {
                System.err.println("ERROR: No 'properties' node found in JSON");
                return;
            }
            
            if (!propertiesNode.isArray()) {
                System.err.println("ERROR: 'properties' node is not an array");
                return;
            }
            
            System.out.println("Found " + propertiesNode.size() + " properties in JSON");
            
            List<Property> properties = new ArrayList<>();
            int successCount = 0;
            int errorCount = 0;
            
            // Parse all properties
            for (JsonNode node : propertiesNode) {
                try {
                    // Debug: print first few properties
                    if (properties.size() < 3) {
                        System.out.println("Processing property: " + node.toString());
                    }
                    
                    Property property = new Property(
                        node.get("id").asLong(),
                        node.get("propertyName").asText(),
                        node.get("propertyLocation").asText(),
                        node.get("propertyCity").asText(),
                        node.get("propertyState").asText(),
                        node.get("propertyCountry").asText(),
                        node.get("propertyAddress").asText(),
                        node.get("propertyPhoneNumber").asText(),
                        node.get("propertyEmailAddress").asText(),
                        node.get("propertyAirportProximity").asText(),
                        node.get("propertyDescription").asText(),
                        node.get("propertyPricePerNight").asText(),
                        node.get("propertyCommissionAmount").asText(),
                        node.get("propertyCancellationPenalty").asText()
                    );
                    properties.add(property);
                    successCount++;
                    
                } catch (Exception e) {
                    System.err.println("Error parsing property: " + e.getMessage());
                    errorCount++;
                    if (errorCount < 5) { // Only show first 5 errors
                        e.printStackTrace();
                    }
                }
            }
            
            System.out.println("Parsed " + successCount + " properties successfully, " + errorCount + " errors");
            
            if (!properties.isEmpty()) {
                System.out.println("Saving properties to database...");
                
                // Save all at once for debugging
                try {
                    propertyService.savePropertiesBatch(properties);
                    long count = propertyRepository.count();
                    System.out.println("Database now contains " + count + " properties");
                } catch (Exception e) {
                    System.err.println("Error saving properties: " + e.getMessage());
                    e.printStackTrace();
                }
            }
            
            long end = System.currentTimeMillis();
            System.out.println("Property loading completed in " + (end - start) + " ms");
            
        } catch (Exception e) {
            System.err.println("Failed to load properties: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Safely extracts a string value from a JSON node.
     *
     * <p>This utility method provides null-safe extraction of string values from JSON nodes.
     * If the field is missing or null, an empty string is returned.
     *
     * @param node the JSON node to extract from
     * @param fieldName the name of the field to extract
     * @return the string value of the field, or empty string if null/missing
     */
    private String getStringValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        return field != null ? field.asText() : "";
    }
    
    /**
     * Safely extracts a numeric value from a JSON node as a string.
     *
     * <p>This utility method handles both numeric and string representations of numbers
     * in JSON. It converts numeric values to their string representation and returns
     * "0" for missing or null fields.
     *
     * @param node the JSON node to extract from
     * @param fieldName the name of the numeric field to extract
     * @return the string representation of the numeric value, or "0" if null/missing
     */
    private String getNumericValue(JsonNode node, String fieldName) {
        JsonNode field = node.get(fieldName);
        if (field != null) {
            if (field.isNumber()) {
                return String.valueOf(field.asDouble());
            } else {
                return field.asText();
            }
        }
        return "0";
    }
}