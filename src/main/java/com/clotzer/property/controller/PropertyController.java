package com.clotzer.property.controller;

import com.clotzer.property.entity.Property;
import com.clotzer.property.repository.PropertyRepository;
import com.clotzer.property.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

/**
 * REST controller providing HTTP endpoints for property data access.
 *
 * <p>This controller exposes RESTful web services for querying property information
 * that has been loaded into the database. It provides endpoints for retrieving
 * all properties and getting property count statistics.
 *
 * <p>All endpoints are mapped under the {@code /api/property} base path and return
 * JSON responses. The controller serves as the presentation layer for the property
 * data, making it accessible via HTTP requests.
 *
 * <p>Available endpoints:
 * <ul>
 *   <li>{@code GET /api/property} - Retrieve all properties</li>
 *   <li>{@code GET /api/property/count} - Get total property count</li>
 * </ul>
 *
 * <p>Response formats:
 * <ul>
 *   <li>Property list: JSON array of property objects</li>
 *   <li>Count response: JSON object with count field</li>
 * </ul>
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 * @see Property
 * @see PropertyRepository
 * @see PropertyService
 */
@RestController
@RequestMapping("/api/property")
public class PropertyController {

    /** Logger for this controller */
    private static final Logger logger = LoggerFactory.getLogger(PropertyController.class);

    /** Repository for direct property database access */
    private final PropertyRepository propertyRepository;

    /** Service layer for property business operations */
    private final PropertyService propertyService;

    /**
     * Constructs a new PropertyController with required dependencies.
     *
     * @param propertyRepository the repository for property database operations
     * @param propertyService the service layer for property business operations
     */
    public PropertyController(PropertyRepository propertyRepository, PropertyService propertyService) {
        this.propertyRepository = propertyRepository;
        this.propertyService = propertyService;
    }

    /**
     * Retrieves all properties from the database.
     *
     * <p>This endpoint returns a complete list of all property records currently
     * stored in the database. Each property includes all available information
     * such as name, location, contact details, pricing, and policies.
     *
     * <p>The response is a JSON array containing property objects. If no properties
     * exist in the database, an empty array is returned.
     *
     * <p>HTTP Method: GET<br>
     * Path: {@code /api/property}<br>
     * Response: JSON array of property objects
     *
     * <p>Example response:
     * <pre>
     * [
     *   {
     *     "id": 1,
     *     "propertyName": "Sunset Resort",
     *     "propertyLocation": "Beachfront",
     *     "propertyCity": "Miami",
     *     "propertyState": "Florida",
     *     "propertyCountry": "USA",
     *     "propertyAddress": "123 Ocean Drive",
     *     "propertyPhoneNumber": "+1-305-555-0123",
     *     "propertyEmailAddress": "info@sunsetresort.com",
     *     "propertyAirportProximity": "10 miles from Miami International",
     *     "propertyDescription": "Beautiful beachfront resort with ocean views",
     *     "propertyPricePerNight": "299.99",
     *     "propertyCommissionAmount": "45.00",
     *     "propertyCancellationPenalty": "50% if cancelled within 48 hours"
     *   },
     *   ...
     * ]
     * </pre>
     *
     * @return a list of all properties in the database
     */
    @GetMapping("")
    public List<Property> findAllProperties() {
        try {
            return propertyRepository.findAll();
        } catch (Exception e) {
            logger.error("Error retrieving all properties", e);
            throw new RuntimeException("Database error");
        }
    }

    /**
     * Retrieves the total count of properties in the database.
     *
     * <p>This endpoint provides a lightweight way to get statistical information
     * about the number of properties currently stored in the database. It's useful
     * for monitoring, reporting, and validating data loading operations.
     *
     * <p>The response is a JSON object containing a single {@code count} field
     * with the total number of properties.
     *
     * <p>HTTP Method: GET<br>
     * Path: {@code /api/property/count}<br>
     * Response: JSON object with count field
     *
     * <p>Example response:
     * <pre>
     * {
     *   "count": 1000
     * }
     * </pre>
     *
     * @return a map containing the total property count under the "count" key
     */
    @GetMapping("/count")
    public Map<String, Long> getPropertyCount() {
        try {
            return Map.of("count", propertyService.getPropertyCount());
        } catch (Exception e) {
            logger.error("Error retrieving property count", e);
            throw new RuntimeException("Service error");
        }
    }

    /**
     * Global exception handler for runtime exceptions.
     *
     * @param e the runtime exception
     * @return a response entity with internal server error status
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
    }
}