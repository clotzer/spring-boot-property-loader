package com.clotzer.property.controller;

import com.clotzer.property.PropertyService;
import com.clotzer.property.controller.PropertyController;
import com.clotzer.property.entity.Property;
import com.clotzer.property.repository.PropertyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for the PropertyController REST API endpoints.
 *
 * <p>This test class verifies the HTTP endpoints provided by the PropertyController,
 * including response formatting, status codes, and integration with service layer.
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 */
@WebMvcTest(PropertyController.class)
class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyRepository propertyRepository;

    @MockBean
    private PropertyService propertyService;

    @Autowired
    private ObjectMapper objectMapper;

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
    @DisplayName("GET /api/property returns all properties successfully")
    void testFindAllPropertiesSuccess() throws Exception {
        // Arrange
        when(propertyRepository.findAll()).thenReturn(testProperties);

        // Act & Assert
        mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].propertyName").value("Test Resort"))
                .andExpect(jsonPath("$[0].propertyLocation").value("Beachfront"))
                .andExpect(jsonPath("$[0].propertyCity").value("Miami"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].propertyName").value("Mountain Lodge"));

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/property returns empty array when no properties exist")
    void testFindAllPropertiesEmpty() throws Exception {
        // Arrange
        when(propertyRepository.findAll()).thenReturn(Arrays.asList());

        // Act & Assert
        mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/property handles repository exception gracefully")
    void testFindAllPropertiesException() throws Exception {
        // Arrange
        when(propertyRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GET /api/property/count returns correct count")
    void testGetPropertyCountSuccess() throws Exception {
        // Arrange
        long expectedCount = 100L;
        when(propertyService.getPropertyCount()).thenReturn(expectedCount);

        // Act & Assert
        mockMvc.perform(get("/api/property/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(100));

        verify(propertyService, times(1)).getPropertyCount();
    }

    @Test
    @DisplayName("GET /api/property/count returns zero when no properties exist")
    void testGetPropertyCountZero() throws Exception {
        // Arrange
        when(propertyService.getPropertyCount()).thenReturn(0L);

        // Act & Assert
        mockMvc.perform(get("/api/property/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(0));

        verify(propertyService, times(1)).getPropertyCount();
    }

    @Test
    @DisplayName("GET /api/property/count handles service exception gracefully")
    void testGetPropertyCountException() throws Exception {
        // Arrange
        when(propertyService.getPropertyCount()).thenThrow(new RuntimeException("Service error"));

        // Act & Assert
        mockMvc.perform(get("/api/property/count")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());

        verify(propertyService, times(1)).getPropertyCount();
    }

    @Test
    @DisplayName("GET /api/property returns single property correctly")
    void testFindAllPropertiesSingleProperty() throws Exception {
        // Arrange
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(testProperty));

        // Act & Assert
        mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].propertyName").value("Test Resort"))
                .andExpect(jsonPath("$[0].propertyDescription").value("Beautiful beachfront resort"))
                .andExpect(jsonPath("$[0].propertyPricePerNight").value("299.99"))
                .andExpect(jsonPath("$[0].propertyCommissionAmount").value("45.00"));

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Verify controller handles large property datasets")
    void testFindAllPropertiesLargeDataset() throws Exception {
        // Arrange
        List<Property> largeDataset = Arrays.asList(new Property[1000]);
        when(propertyRepository.findAll()).thenReturn(largeDataset);

        // Act & Assert
        mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1000));

        verify(propertyRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test property JSON serialization includes all fields")
    void testPropertyJsonSerialization() throws Exception {
        // Arrange
        when(propertyRepository.findAll()).thenReturn(Arrays.asList(testProperty));

        // Act & Assert - Verify all property fields are serialized
        mockMvc.perform(get("/api/property")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].propertyName").exists())
                .andExpect(jsonPath("$[0].propertyLocation").exists())
                .andExpect(jsonPath("$[0].propertyCity").exists())
                .andExpect(jsonPath("$[0].propertyState").exists())
                .andExpect(jsonPath("$[0].propertyCountry").exists())
                .andExpect(jsonPath("$[0].propertyAddress").exists())
                .andExpect(jsonPath("$[0].propertyPhoneNumber").exists())
                .andExpect(jsonPath("$[0].propertyEmailAddress").exists())
                .andExpect(jsonPath("$[0].propertyAirportProximity").exists())
                .andExpect(jsonPath("$[0].propertyDescription").exists())
                .andExpect(jsonPath("$[0].propertyPricePerNight").exists())
                .andExpect(jsonPath("$[0].propertyCommissionAmount").exists())
                .andExpect(jsonPath("$[0].propertyCancellationPenalty").exists());

        verify(propertyRepository, times(1)).findAll();
    }
}
