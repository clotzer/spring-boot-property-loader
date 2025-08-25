package com.clotzer.property;

import com.clotzer.property.entity.Property;
import com.clotzer.property.repository.PropertyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for property-related business operations and database transactions.
 *
 * <p>This service provides transactional methods for property data management, ensuring
 * data consistency and proper error handling during database operations. It serves as
 * an intermediary between the data access layer and the application components.
 *
 * <p>Key features:
 * <ul>
 *   <li>Transactional property saving with individual and batch operations</li>
 *   <li>Comprehensive error handling and logging for database operations</li>
 *   <li>Property count retrieval for monitoring and reporting</li>
 *   <li>Automatic rollback on transaction failures</li>
 *   <li>Detailed progress logging for debugging and monitoring</li>
 * </ul>
 *
 * <p>All save operations are wrapped in transactions to ensure data integrity.
 * If any error occurs during a transaction, the operation is automatically rolled back
 * to maintain database consistency.
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 * @see Property
 * @see PropertyRepository
 * @see DataLoader
 */
@Service
public class PropertyService {

    /** Repository for property database operations */
    private final PropertyRepository propertyRepository;

    /**
     * Constructs a new PropertyService with the required repository dependency.
     *
     * @param propertyRepository the repository for property database operations
     */
    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    /**
     * Saves a single property to the database.
     *
     * <p>This method persists a property entity to the database with logging
     * for debugging and monitoring purposes. It provides detailed logging of
     * the save operation including property ID and name for traceability.
     *
     * <p>The save operation is performed within the transaction context of the
     * calling method. For batch operations, consider using
     * {@link #savePropertiesBatch(List)} for better performance.
     *
     * <p>Logging details:
     * <ul>
     *   <li>Pre-save: Property ID and name</li>
     *   <li>Post-save: Confirmation with saved property ID</li>
     *   <li>Error: Exception details for troubleshooting</li>
     * </ul>
     *
     * @param property the property entity to save
     * @throws IllegalArgumentException if property is null
     * @throws RuntimeException if the save operation fails
     */
    public void saveProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("Property cannot be null");
        }

        try {
            System.out.println("Saving property id=" + property.getId() + ", name=" + property.getPropertyName());
            Property saved = propertyRepository.save(property);
            System.out.println("Successfully saved property with id=" + saved.getId());
        } catch (Exception e) {
            System.err.println("Error saving property id=" + property.getId() + ": " + e.getMessage());
            throw e;
        }
    }

    /**
     * Saves a batch of properties to the database within a single transaction.
     *
     * <p>This method provides efficient batch processing for multiple properties,
     * using a single transaction to ensure all properties are saved together or
     * none at all. This approach is significantly more efficient than individual
     * saves when processing large datasets.
     *
     * <p>Performance benefits:
     * <ul>
     *   <li>Single transaction for the entire batch</li>
     *   <li>Reduced database round trips</li>
     *   <li>Atomic operation - all succeed or all fail</li>
     *   <li>Progress logging for monitoring large batches</li>
     * </ul>
     *
     * <p>Error handling:
     * <ul>
     *   <li>Automatic rollback if any property in the batch fails</li>
     *   <li>Detailed error logging with stack trace</li>
     *   <li>Exception re-throwing for upstream error handling</li>
     * </ul>
     *
     * @param properties the list of property entities to save
     * @throws IllegalArgumentException if properties list is null
     * @throws RuntimeException if the batch save operation fails
     */
    @Transactional
    public void savePropertiesBatch(List<Property> properties) {
        if (properties == null) {
            throw new IllegalArgumentException("Properties list cannot be null");
        }

        try {
            System.out.println("Saving batch of " + properties.size() + " properties");
            List<Property> saved = propertyRepository.saveAll(properties);
            System.out.println("Successfully saved " + saved.size() + " properties");
        } catch (Exception e) {
            System.err.println("Error saving batch: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Batch save failed", e);
        }
    }

    /**
     * Retrieves the total count of properties in the database.
     *
     * <p>This method provides a quick way to get the current number of properties
     * stored in the database, useful for monitoring, reporting, and validation
     * purposes after data loading operations.
     *
     * <p>This is a read-only operation that does not require a transaction and
     * can be safely called at any time without affecting database performance.
     *
     * @return the total number of properties in the database
     */
    public long getPropertyCount() {
        return propertyRepository.count();
    }
}