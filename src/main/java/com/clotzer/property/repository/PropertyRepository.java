package com.clotzer.property.repository;

import com.clotzer.property.entity.Property;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository interface for Property entity database operations.
 *
 * <p>This interface extends Spring Data's {@link ListCrudRepository} to provide
 * standard CRUD (Create, Read, Update, Delete) operations for Property entities.
 * It serves as the data access layer for property information, abstracting
 * database interactions through Spring Data JPA.
 *
 * <p>Key features provided by extending {@code ListCrudRepository}:
 * <ul>
 *   <li>{@code save(Property)} - Save a single property</li>
 *   <li>{@code saveAll(Iterable<Property>)} - Save multiple properties in batch</li>
 *   <li>{@code findById(Long)} - Find property by ID</li>
 *   <li>{@code findAll()} - Retrieve all properties as a List</li>
 *   <li>{@code count()} - Get total count of properties</li>
 *   <li>{@code delete(Property)} - Delete a property</li>
 *   <li>{@code deleteById(Long)} - Delete property by ID</li>
 *   <li>{@code existsById(Long)} - Check if property exists by ID</li>
 * </ul>
 *
 * <p>The repository automatically handles:
 * <ul>
 *   <li>SQL generation for all CRUD operations</li>
 *   <li>Entity-to-table mapping based on JPA annotations</li>
 *   <li>Transaction management when used with {@code @Transactional}</li>
 *   <li>Exception translation to Spring's DataAccessException hierarchy</li>
 * </ul>
 *
 * <p>Custom query methods can be added by following Spring Data JPA naming conventions
 * or by using {@code @Query} annotations. For example:
 * <ul>
 *   <li>{@code List<Property> findByPropertyCity(String city)}</li>
 *   <li>{@code List<Property> findByPropertyCountry(String country)}</li>
 *   <li>{@code List<Property> findByPropertyLocationContaining(String location)}</li>
 * </ul>
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 * @see Property
 * @see org.springframework.data.repository.ListCrudRepository
 */
public interface PropertyRepository extends ListCrudRepository<Property, Long> {
    // Standard CRUD operations are inherited from ListCrudRepository
    // Custom query methods can be added here following Spring Data JPA conventions
}
