package com.clotzer.property.service;

import com.clotzer.property.PropertyService;
import com.clotzer.property.entity.Property;
import com.clotzer.property.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the PropertyService class.
 *
 * <p>This test class verifies the business logic and transactional behavior
 * of the PropertyService, including save operations and property counting.
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 */
@ExtendWith(MockitoExtension.class)
class PropertyServiceTest {

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private Property testProperty;
    private List<Property> testProperties;

    @BeforeEach
    void setUp() {
        testProperty = new Property(
            1L, "Test Resort", "Beachfront", "Miami", "Florida", "USA",
            "123 Ocean Drive", "+1-305-555-0123", "info@testresort.com",
            "10 miles from Miami International", "Beautiful beachfront resort",
            "299.99", "45.00", "50% if cancelled within 48 hours"
        );

        Property secondProperty = new Property(
            2L, "Mountain Lodge", "Mountain View", "Denver", "Colorado", "USA",
            "456 Pine Street", "+1-303-555-0456", "info@mountainlodge.com",
            "20 miles from Denver International", "Cozy mountain retreat",
            "199.99", "30.00", "25% if cancelled within 24 hours"
        );

        testProperties = Arrays.asList(testProperty, secondProperty);
    }

    @Test
    @DisplayName("Test constructor initializes repository dependency")
    void testConstructor() {
        PropertyService service = new PropertyService(propertyRepository);
        assertNotNull(service);
    }

    @Test
    @DisplayName("Test saveProperty successfully saves a single property")
    void testSavePropertySuccess() {
        // Arrange
        when(propertyRepository.save(testProperty)).thenReturn(testProperty);

        // Act & Assert - Should not throw any exception
        assertDoesNotThrow(() -> propertyService.saveProperty(testProperty));

        // Verify
        verify(propertyRepository, times(1)).save(testProperty);
    }

    @Test
    @DisplayName("Test saveProperty throws exception when repository fails")
    void testSavePropertyThrowsException() {
        // Arrange
        RuntimeException expectedException = new RuntimeException("Database error");
        when(propertyRepository.save(testProperty)).thenThrow(expectedException);

        // Act & Assert
        RuntimeException thrownException = assertThrows(RuntimeException.class,
            () -> propertyService.saveProperty(testProperty));

        assertEquals("Database error", thrownException.getMessage());
        verify(propertyRepository, times(1)).save(testProperty);
    }

    @Test
    @DisplayName("Test saveProperty throws exception for null property")
    void testSavePropertyWithNullProperty() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> propertyService.saveProperty(null));

        // Verify repository is never called when parameter validation fails
        verifyNoInteractions(propertyRepository);
    }

    @Test
    @DisplayName("Test savePropertiesBatch successfully saves multiple properties")
    void testSavePropertiesBatchSuccess() {
        // Arrange
        when(propertyRepository.saveAll(testProperties)).thenReturn(testProperties);

        // Act & Assert - Should not throw any exception
        assertDoesNotThrow(() -> propertyService.savePropertiesBatch(testProperties));

        // Verify
        verify(propertyRepository, times(1)).saveAll(testProperties);
    }

    @Test
    @DisplayName("Test savePropertiesBatch throws exception when repository fails")
    void testSavePropertiesBatchThrowsException() {
        // Arrange
        RuntimeException expectedException = new RuntimeException("Batch save failed");
        when(propertyRepository.saveAll(testProperties)).thenThrow(expectedException);

        // Act & Assert
        RuntimeException thrownException = assertThrows(RuntimeException.class,
            () -> propertyService.savePropertiesBatch(testProperties));

        assertEquals("Batch save failed", thrownException.getMessage());
        verify(propertyRepository, times(1)).saveAll(testProperties);
    }

    @Test
    @DisplayName("Test savePropertiesBatch with empty list")
    void testSavePropertiesBatchWithEmptyList() {
        // Arrange
        List<Property> emptyList = Arrays.asList();
        when(propertyRepository.saveAll(emptyList)).thenReturn(emptyList);

        // Act & Assert - Should not throw any exception
        assertDoesNotThrow(() -> propertyService.savePropertiesBatch(emptyList));

        // Verify
        verify(propertyRepository, times(1)).saveAll(emptyList);
    }

    @Test
    @DisplayName("Test savePropertiesBatch throws exception for null list")
    void testSavePropertiesBatchWithNullList() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> propertyService.savePropertiesBatch(null));

        // Verify repository is never called when parameter validation fails
        verifyNoInteractions(propertyRepository);
    }

    @Test
    @DisplayName("Test getPropertyCount returns correct count")
    void testGetPropertyCount() {
        // Arrange
        long expectedCount = 100L;
        when(propertyRepository.count()).thenReturn(expectedCount);

        // Act
        long actualCount = propertyService.getPropertyCount();

        // Assert
        assertEquals(expectedCount, actualCount);
        verify(propertyRepository, times(1)).count();
    }

    @Test
    @DisplayName("Test getPropertyCount returns zero when no properties exist")
    void testGetPropertyCountWhenEmpty() {
        // Arrange
        when(propertyRepository.count()).thenReturn(0L);

        // Act
        long actualCount = propertyService.getPropertyCount();

        // Assert
        assertEquals(0L, actualCount);
        verify(propertyRepository, times(1)).count();
    }

    @Test
    @DisplayName("Test getPropertyCount handles repository exception")
    void testGetPropertyCountThrowsException() {
        // Arrange
        when(propertyRepository.count()).thenThrow(new RuntimeException("Database connection failed"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> propertyService.getPropertyCount());
        verify(propertyRepository, times(1)).count();
    }

    @Test
    @DisplayName("Test savePropertiesBatch with large dataset")
    void testSavePropertiesBatchWithLargeDataset() {
        // Arrange
        List<Property> largeDataset = Arrays.asList(new Property[1000]);
        when(propertyRepository.saveAll(largeDataset)).thenReturn(largeDataset);

        // Act & Assert - Should handle large datasets without issues
        assertDoesNotThrow(() -> propertyService.savePropertiesBatch(largeDataset));

        verify(propertyRepository, times(1)).saveAll(largeDataset);
    }
}
