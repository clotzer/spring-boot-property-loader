package com.clotzer.property.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Property entity class.
 *
 * <p>This test class verifies the functionality of the Property JPA entity,
 * including constructors, getters, setters, and data integrity.
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 */
class PropertyTest {

    private Property property;
    private static final long TEST_ID = 1L;
    private static final String TEST_PROPERTY_NAME = "Test Resort";
    private static final String TEST_PROPERTY_LOCATION = "Beachfront";
    private static final String TEST_PROPERTY_CITY = "Miami";
    private static final String TEST_PROPERTY_STATE = "Florida";
    private static final String TEST_PROPERTY_COUNTRY = "USA";
    private static final String TEST_PROPERTY_ADDRESS = "123 Ocean Drive";
    private static final String TEST_PROPERTY_PHONE = "+1-305-555-0123";
    private static final String TEST_PROPERTY_EMAIL = "info@testresort.com";
    private static final String TEST_AIRPORT_PROXIMITY = "10 miles from Miami International";
    private static final String TEST_PROPERTY_DESCRIPTION = "A beautiful beachfront resort with stunning ocean views";
    private static final String TEST_PRICE_PER_NIGHT = "299.99";
    private static final String TEST_COMMISSION_AMOUNT = "45.00";
    private static final String TEST_CANCELLATION_PENALTY = "50% if cancelled within 48 hours";

    @BeforeEach
    void setUp() {
        property = new Property();
    }

    @Test
    @DisplayName("Test default constructor creates empty Property")
    void testDefaultConstructor() {
        Property emptyProperty = new Property();
        assertNotNull(emptyProperty);
        assertEquals(0L, emptyProperty.getId());
        assertNull(emptyProperty.getPropertyName());
    }

    @Test
    @DisplayName("Test parameterized constructor with all fields")
    void testParameterizedConstructor() {
        Property fullProperty = new Property(
            TEST_ID, TEST_PROPERTY_NAME, TEST_PROPERTY_LOCATION, TEST_PROPERTY_CITY,
            TEST_PROPERTY_STATE, TEST_PROPERTY_COUNTRY, TEST_PROPERTY_ADDRESS,
            TEST_PROPERTY_PHONE, TEST_PROPERTY_EMAIL, TEST_AIRPORT_PROXIMITY,
            TEST_PROPERTY_DESCRIPTION, TEST_PRICE_PER_NIGHT, TEST_COMMISSION_AMOUNT,
            TEST_CANCELLATION_PENALTY
        );

        assertEquals(TEST_ID, fullProperty.getId());
        assertEquals(TEST_PROPERTY_NAME, fullProperty.getPropertyName());
        assertEquals(TEST_PROPERTY_LOCATION, fullProperty.getPropertyLocation());
        assertEquals(TEST_PROPERTY_CITY, fullProperty.getPropertyCity());
        assertEquals(TEST_PROPERTY_STATE, fullProperty.getPropertyState());
        assertEquals(TEST_PROPERTY_COUNTRY, fullProperty.getPropertyCountry());
        assertEquals(TEST_PROPERTY_ADDRESS, fullProperty.getPropertyAddress());
        assertEquals(TEST_PROPERTY_PHONE, fullProperty.getPropertyPhoneNumber());
        assertEquals(TEST_PROPERTY_EMAIL, fullProperty.getPropertyEmailAddress());
        assertEquals(TEST_AIRPORT_PROXIMITY, fullProperty.getPropertyAirportProximity());
        assertEquals(TEST_PROPERTY_DESCRIPTION, fullProperty.getPropertyDescription());
        assertEquals(TEST_PRICE_PER_NIGHT, fullProperty.getPropertyPricePerNight());
        assertEquals(TEST_COMMISSION_AMOUNT, fullProperty.getPropertyCommissionAmount());
        assertEquals(TEST_CANCELLATION_PENALTY, fullProperty.getPropertyCancellationPenalty());
    }

    @Test
    @DisplayName("Test ID getter and setter")
    void testIdGetterAndSetter() {
        property.setId(TEST_ID);
        assertEquals(TEST_ID, property.getId());
    }

    @Test
    @DisplayName("Test property name getter and setter")
    void testPropertyNameGetterAndSetter() {
        property.setPropertyName(TEST_PROPERTY_NAME);
        assertEquals(TEST_PROPERTY_NAME, property.getPropertyName());
    }

    @Test
    @DisplayName("Test property location getter and setter")
    void testPropertyLocationGetterAndSetter() {
        property.setPropertyLocation(TEST_PROPERTY_LOCATION);
        assertEquals(TEST_PROPERTY_LOCATION, property.getPropertyLocation());
    }

    @Test
    @DisplayName("Test property city getter and setter")
    void testPropertyCityGetterAndSetter() {
        property.setPropertyCity(TEST_PROPERTY_CITY);
        assertEquals(TEST_PROPERTY_CITY, property.getPropertyCity());
    }

    @Test
    @DisplayName("Test property state getter and setter")
    void testPropertyStateGetterAndSetter() {
        property.setPropertyState(TEST_PROPERTY_STATE);
        assertEquals(TEST_PROPERTY_STATE, property.getPropertyState());
    }

    @Test
    @DisplayName("Test property country getter and setter")
    void testPropertyCountryGetterAndSetter() {
        property.setPropertyCountry(TEST_PROPERTY_COUNTRY);
        assertEquals(TEST_PROPERTY_COUNTRY, property.getPropertyCountry());
    }

    @Test
    @DisplayName("Test property address getter and setter")
    void testPropertyAddressGetterAndSetter() {
        property.setPropertyAddress(TEST_PROPERTY_ADDRESS);
        assertEquals(TEST_PROPERTY_ADDRESS, property.getPropertyAddress());
    }

    @Test
    @DisplayName("Test property phone number getter and setter")
    void testPropertyPhoneNumberGetterAndSetter() {
        property.setPropertyPhoneNumber(TEST_PROPERTY_PHONE);
        assertEquals(TEST_PROPERTY_PHONE, property.getPropertyPhoneNumber());
    }

    @Test
    @DisplayName("Test property email address getter and setter")
    void testPropertyEmailAddressGetterAndSetter() {
        property.setPropertyEmailAddress(TEST_PROPERTY_EMAIL);
        assertEquals(TEST_PROPERTY_EMAIL, property.getPropertyEmailAddress());
    }

    @Test
    @DisplayName("Test property airport proximity getter and setter")
    void testPropertyAirportProximityGetterAndSetter() {
        property.setPropertyAirportProximity(TEST_AIRPORT_PROXIMITY);
        assertEquals(TEST_AIRPORT_PROXIMITY, property.getPropertyAirportProximity());
    }

    @Test
    @DisplayName("Test property description getter and setter")
    void testPropertyDescriptionGetterAndSetter() {
        property.setPropertyDescription(TEST_PROPERTY_DESCRIPTION);
        assertEquals(TEST_PROPERTY_DESCRIPTION, property.getPropertyDescription());
    }

    @Test
    @DisplayName("Test property price per night getter and setter")
    void testPropertyPricePerNightGetterAndSetter() {
        property.setPropertyPricePerNight(TEST_PRICE_PER_NIGHT);
        assertEquals(TEST_PRICE_PER_NIGHT, property.getPropertyPricePerNight());
    }

    @Test
    @DisplayName("Test property commission amount getter and setter")
    void testPropertyCommissionAmountGetterAndSetter() {
        property.setPropertyCommissionAmount(TEST_COMMISSION_AMOUNT);
        assertEquals(TEST_COMMISSION_AMOUNT, property.getPropertyCommissionAmount());
    }

    @Test
    @DisplayName("Test property cancellation penalty getter and setter")
    void testPropertyCancellationPenaltyGetterAndSetter() {
        property.setPropertyCancellationPenalty(TEST_CANCELLATION_PENALTY);
        assertEquals(TEST_CANCELLATION_PENALTY, property.getPropertyCancellationPenalty());
    }

    @Test
    @DisplayName("Test setting null values")
    void testNullValues() {
        property.setPropertyName(null);
        property.setPropertyDescription(null);

        assertNull(property.getPropertyName());
        assertNull(property.getPropertyDescription());
    }

    @Test
    @DisplayName("Test setting empty strings")
    void testEmptyStrings() {
        property.setPropertyName("");
        property.setPropertyLocation("");

        assertEquals("", property.getPropertyName());
        assertEquals("", property.getPropertyLocation());
    }

    @Test
    @DisplayName("Test long property description")
    void testLongPropertyDescription() {
        String longDescription = "A".repeat(1000); // Test VARCHAR(1000) limit
        property.setPropertyDescription(longDescription);
        assertEquals(longDescription, property.getPropertyDescription());
    }
}
