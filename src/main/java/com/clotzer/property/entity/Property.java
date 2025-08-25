package com.clotzer.property.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * JPA entity representing a property in the real estate system.
 *
 * <p>This entity maps to the {@code property} table in the MySQL database and contains
 * comprehensive information about rental properties including location details, contact
 * information, pricing, and descriptive content.
 *
 * <p>Key features:
 * <ul>
 *   <li>Complete property information including name, location, and contact details</li>
 *   <li>Pricing information with commission amounts and cancellation policies</li>
 *   <li>Extended property descriptions up to 1000 characters</li>
 *   <li>Airport proximity information for travel convenience</li>
 *   <li>Flexible string-based storage for all attributes to handle various data formats</li>
 * </ul>
 *
 * <p>Database mapping:
 * <ul>
 *   <li>Table: {@code property}</li>
 *   <li>Primary key: {@code id} (long)</li>
 *   <li>Property description: VARCHAR(1000) to accommodate longer descriptions</li>
 *   <li>All other fields: Default VARCHAR(255)</li>
 * </ul>
 *
 * @author Carey Lotzer
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "property")
public class Property {

    /** Unique identifier for the property */
    @Id
    private long id;

    /** Name of the property (e.g., "Sunset Villa Resort") */
    private String propertyName;

    /** Location type of the property (e.g., "Beachfront", "Downtown") */
    private String propertyLocation;

    /** City where the property is located */
    private String propertyCity;

    /** State or province where the property is located */
    private String propertyState;

    /** Country where the property is located */
    private String propertyCountry;

    /** Full street address of the property */
    private String propertyAddress;

    /** Contact phone number for the property */
    private String propertyPhoneNumber;

    /** Contact email address for the property */
    private String propertyEmailAddress;

    /** Information about nearby airport and distance */
    private String propertyAirportProximity;

    /**
     * Detailed description of the property including amenities and features.
     * Configured with extended length to accommodate comprehensive descriptions.
     */
    @Column(length = 1000)
    private String propertyDescription;

    /** Nightly rate for the property as a string to handle various formats */
    private String propertyPricePerNight;

    /** Commission amount earned from bookings */
    private String propertyCommissionAmount;

    /** Cancellation policy and penalty information */
    private String propertyCancellationPenalty;

    /**
     * Default constructor required by JPA.
     * Creates an empty Property instance.
     */
    public Property() {}

    /**
     * Constructs a new Property with all required fields.
     *
     * <p>This constructor is primarily used during JSON data loading to create
     * fully populated Property instances from parsed JSON data.
     *
     * @param id unique identifier for the property
     * @param propertyName name of the property
     * @param propertyLocation location type (e.g., "Beachfront", "Downtown")
     * @param propertyCity city where the property is located
     * @param propertyState state or province where the property is located
     * @param propertyCountry country where the property is located
     * @param propertyAddress full street address of the property
     * @param propertyPhoneNumber contact phone number
     * @param propertyEmailAddress contact email address
     * @param propertyAirportProximity airport proximity information
     * @param propertyDescription detailed description with amenities
     * @param propertyPricePerNight nightly rate for the property
     * @param propertyCommissionAmount commission amount from bookings
     * @param propertyCancellationPenalty cancellation policy information
     */
    public Property(long id, String propertyName, String propertyLocation, String propertyCity, String propertyState,
                    String propertyCountry, String propertyAddress, String propertyPhoneNumber, String propertyEmailAddress,
                    String propertyAirportProximity, String propertyDescription, String propertyPricePerNight, String propertyCommissionAmount,
                    String propertyCancellationPenalty) {
        this.id = id;
        this.propertyName = propertyName;
        this.propertyLocation = propertyLocation;
        this.propertyCity = propertyCity;
        this.propertyState = propertyState;
        this.propertyCountry = propertyCountry;
        this.propertyAddress = propertyAddress;
        this.propertyPhoneNumber = propertyPhoneNumber;
        this.propertyEmailAddress = propertyEmailAddress;
        this.propertyAirportProximity = propertyAirportProximity;
        this.propertyDescription = propertyDescription;
        this.propertyPricePerNight = propertyPricePerNight;
        this.propertyCommissionAmount = propertyCommissionAmount;
        this.propertyCancellationPenalty = propertyCancellationPenalty;
    }

    // Getters and setters for all fields

    /**
     * Gets the unique identifier for this property.
     * @return the property ID
     */
    public long getId() { return id; }

    /**
     * Sets the unique identifier for this property.
     * @param id the property ID to set
     */
    public void setId(long id) { this.id = id; }

    /**
     * Gets the name of the property.
     * @return the property name
     */
    public String getPropertyName() { return propertyName; }

    /**
     * Sets the name of the property.
     * @param propertyName the property name to set
     */
    public void setPropertyName(String propertyName) { this.propertyName = propertyName; }

    /**
     * Gets the location type of the property.
     * @return the property location type (e.g., "Beachfront", "Downtown")
     */
    public String getPropertyLocation() { return propertyLocation; }

    /**
     * Sets the location type of the property.
     * @param propertyLocation the property location type to set
     */
    public void setPropertyLocation(String propertyLocation) { this.propertyLocation = propertyLocation; }

    /**
     * Gets the city where the property is located.
     * @return the property city
     */
    public String getPropertyCity() { return propertyCity; }

    /**
     * Sets the city where the property is located.
     * @param propertyCity the property city to set
     */
    public void setPropertyCity(String propertyCity) { this.propertyCity = propertyCity; }

    /**
     * Gets the state or province where the property is located.
     * @return the property state
     */
    public String getPropertyState() { return propertyState; }

    /**
     * Sets the state or province where the property is located.
     * @param propertyState the property state to set
     */
    public void setPropertyState(String propertyState) { this.propertyState = propertyState; }

    /**
     * Gets the country where the property is located.
     * @return the property country
     */
    public String getPropertyCountry() { return propertyCountry; }

    /**
     * Sets the country where the property is located.
     * @param propertyCountry the property country to set
     */
    public void setPropertyCountry(String propertyCountry) { this.propertyCountry = propertyCountry; }

    /**
     * Gets the full street address of the property.
     * @return the property address
     */
    public String getPropertyAddress() { return propertyAddress; }

    /**
     * Sets the full street address of the property.
     * @param propertyAddress the property address to set
     */
    public void setPropertyAddress(String propertyAddress) { this.propertyAddress = propertyAddress; }

    /**
     * Gets the contact phone number for the property.
     * @return the property phone number
     */
    public String getPropertyPhoneNumber() { return propertyPhoneNumber; }

    /**
     * Sets the contact phone number for the property.
     * @param propertyPhoneNumber the property phone number to set
     */
    public void setPropertyPhoneNumber(String propertyPhoneNumber) { this.propertyPhoneNumber = propertyPhoneNumber; }

    /**
     * Gets the contact email address for the property.
     * @return the property email address
     */
    public String getPropertyEmailAddress() { return propertyEmailAddress; }

    /**
     * Sets the contact email address for the property.
     * @param propertyEmailAddress the property email address to set
     */
    public void setPropertyEmailAddress(String propertyEmailAddress) { this.propertyEmailAddress = propertyEmailAddress; }

    /**
     * Gets the airport proximity information for the property.
     * @return the airport proximity details
     */
    public String getPropertyAirportProximity() { return propertyAirportProximity; }

    /**
     * Sets the airport proximity information for the property.
     * @param propertyAirportProximity the airport proximity details to set
     */
    public void setPropertyAirportProximity(String propertyAirportProximity) { this.propertyAirportProximity = propertyAirportProximity; }

    /**
     * Gets the detailed description of the property.
     * @return the property description including amenities and features
     */
    public String getPropertyDescription() { return propertyDescription; }

    /**
     * Sets the detailed description of the property.
     * @param propertyDescription the property description to set
     */
    public void setPropertyDescription(String propertyDescription) { this.propertyDescription = propertyDescription; }

    /**
     * Gets the nightly rate for the property.
     * @return the property price per night
     */
    public String getPropertyPricePerNight() { return propertyPricePerNight; }

    /**
     * Sets the nightly rate for the property.
     * @param propertyPricePerNight the property price per night to set
     */
    public void setPropertyPricePerNight(String propertyPricePerNight) { this.propertyPricePerNight = propertyPricePerNight; }

    /**
     * Gets the commission amount earned from property bookings.
     * @return the property commission amount
     */
    public String getPropertyCommissionAmount() { return propertyCommissionAmount; }

    /**
     * Sets the commission amount earned from property bookings.
     * @param propertyCommissionAmount the property commission amount to set
     */
    public void setPropertyCommissionAmount(String propertyCommissionAmount) { this.propertyCommissionAmount = propertyCommissionAmount; }

    /**
     * Gets the cancellation policy and penalty information.
     * @return the property cancellation penalty details
     */
    public String getPropertyCancellationPenalty() { return propertyCancellationPenalty; }

    /**
     * Sets the cancellation policy and penalty information.
     * @param propertyCancellationPenalty the property cancellation penalty details to set
     */
    public void setPropertyCancellationPenalty(String propertyCancellationPenalty) { this.propertyCancellationPenalty = propertyCancellationPenalty; }
}
