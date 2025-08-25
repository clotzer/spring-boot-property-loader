# Property Data Generation Script
# Author: Carey Lotzer
# Generates realistic property data for the Spring Boot Property Loader application

import json
import random
from faker import Faker

fake = Faker()
Faker.seed(1234)
random.seed(1234)

locations = [
    "Beachfront", "Mountain View", "Downtown", "Desert", "Oceanfront", "Historic District",
    "Lakeside", "Countryside", "City Center", "Riverside", "Business District", "Wine Country",
    "Government District", "Tech Hub", "Plains", "Valley", "Forest", "Industrial District",
    "Gulf Coast", "Bay Area", "Ranch Land", "Wilderness", "Tropical Beach", "Financial District",
    "Coastal", "Suburban", "University District", "Ski Resort", "Riverfront", "Island"
]

# Property amenities by location type
location_amenities = {
    "Beachfront": ["private beach access", "ocean views", "beachside dining", "water sports equipment", "sunset terraces"],
    "Mountain View": ["hiking trails", "mountain vistas", "fireplaces", "ski access", "nature walks"],
    "Downtown": ["city skyline views", "rooftop bar", "business center", "metro access", "shopping nearby"],
    "Desert": ["desert landscapes", "stargazing decks", "spa services", "golf course", "pool oasis"],
    "Oceanfront": ["panoramic ocean views", "private balconies", "seafood restaurant", "marina access", "lighthouse views"],
    "Historic District": ["heritage architecture", "antique furnishings", "guided tours", "period details", "cobblestone streets"],
    "Lakeside": ["lake views", "fishing pier", "boat rentals", "lakefront dining", "swimming area"],
    "Countryside": ["rolling hills", "farm-to-table dining", "horseback riding", "wine tasting", "pastoral views"],
    "City Center": ["urban sophistication", "fine dining", "theater district", "shopping centers", "nightlife"],
    "Riverside": ["river views", "kayak rentals", "riverside trails", "fishing spots", "peaceful setting"],
    "Business District": ["conference facilities", "executive services", "high-speed internet", "corporate rates", "meeting rooms"],
    "Wine Country": ["vineyard views", "wine tastings", "cellar tours", "gourmet dining", "harvest experiences"],
    "Government District": ["historic landmarks", "monument views", "political tours", "security features", "diplomatic services"],
    "Tech Hub": ["high-tech amenities", "fast WiFi", "co-working spaces", "innovation center", "startup networking"],
    "Plains": ["wide open spaces", "prairie views", "outdoor activities", "peaceful atmosphere", "starry nights"],
    "Valley": ["valley panoramas", "scenic drives", "hiking paths", "fresh air", "mountain backdrops"],
    "Forest": ["forest trails", "wildlife viewing", "cabin atmosphere", "outdoor adventures", "nature immersion"],
    "Industrial District": ["modern design", "converted warehouses", "urban loft style", "art galleries", "trendy restaurants"],
    "Gulf Coast": ["gulf waters", "seafood cuisine", "coastal charm", "fishing charters", "beach activities"],
    "Bay Area": ["bay views", "maritime culture", "sailing opportunities", "waterfront dining", "harbor tours"],
    "Ranch Land": ["ranch experiences", "horseback riding", "cattle drives", "cowboy culture", "wide open spaces"],
    "Wilderness": ["untouched nature", "wildlife encounters", "adventure activities", "eco-tours", "camping options"],
    "Tropical Beach": ["white sand beaches", "palm trees", "tropical drinks", "snorkeling", "paradise setting"],
    "Financial District": ["luxury accommodations", "business services", "upscale dining", "premium location", "executive floors"],
    "Coastal": ["coastal breezes", "seaside charm", "lighthouse tours", "beach walks", "maritime history"],
    "Suburban": ["family-friendly", "quiet neighborhoods", "local attractions", "easy access", "comfortable setting"],
    "University District": ["academic atmosphere", "student discounts", "cultural events", "library access", "campus tours"],
    "Ski Resort": ["ski slopes", "winter sports", "alpine dining", "mountain lodges", "snow activities"],
    "Riverfront": ["riverfront location", "water activities", "scenic beauty", "peaceful setting", "nature walks"],
    "Island": ["island paradise", "tropical setting", "water activities", "secluded beaches", "resort atmosphere"]
}

# Property type descriptions
property_type_features = {
    "Resort": ["luxury amenities", "spa services", "multiple restaurants", "recreational facilities", "concierge service"],
    "Hotel": ["comfortable rooms", "room service", "front desk", "housekeeping", "guest services"],
    "Lodge": ["rustic charm", "cozy atmosphere", "outdoor activities", "lodge dining", "nature setting"],
    "Inn": ["intimate setting", "personalized service", "local charm", "historic character", "boutique experience"],
    "Suites": ["spacious accommodations", "separate living areas", "kitchen facilities", "extended stay options", "business amenities"],
    "Manor": ["elegant architecture", "luxurious interiors", "historic grandeur", "fine dining", "exclusive atmosphere"],
    "Retreat": ["peaceful environment", "wellness programs", "meditation spaces", "healthy cuisine", "rejuvenation focus"],
    "Cabin": ["rustic accommodations", "nature immersion", "outdoor adventures", "cozy interiors", "campfire areas"]
}

def generate_property_description(property_name, location, country):
    """Generate a realistic property description based on name, location, and country"""

    # Extract property type from name
    property_type = None
    for ptype in property_type_features.keys():
        if ptype in property_name:
            property_type = ptype
            break

    if not property_type:
        property_type = "Hotel"  # default

    # Get location-specific amenities
    amenities = location_amenities.get(location, ["scenic views", "comfortable accommodations", "friendly service"])

    # Get property type features
    type_features = property_type_features.get(property_type, ["quality accommodations", "excellent service"])

    # Create description components
    opening_lines = [
        f"Discover the perfect blend of comfort and luxury at {property_name}.",
        f"Experience exceptional hospitality at {property_name}.",
        f"Welcome to {property_name}, where comfort meets elegance.",
        f"Nestled in a prime {location.lower()} location, {property_name} offers an unforgettable experience.",
        f"{property_name} provides the ideal escape for discerning travelers."
    ]

    location_descriptions = {
        "Beachfront": "steps away from pristine sandy beaches",
        "Mountain View": "surrounded by majestic mountain peaks",
        "Downtown": "in the heart of the bustling city center",
        "Desert": "amidst stunning desert landscapes",
        "Oceanfront": "with breathtaking ocean vistas",
        "Historic District": "steeped in rich cultural heritage",
        "Lakeside": "overlooking tranquil lake waters",
        "Countryside": "set in picturesque rural surroundings",
        "City Center": "at the pulse of urban excitement",
        "Riverside": "along the peaceful riverbank",
        "Wine Country": "surrounded by rolling vineyards",
        "Ski Resort": "at the base of world-class ski slopes",
        "Island": "on a secluded tropical island"
    }

    # Build the description
    opening = random.choice(opening_lines)

    location_desc = location_descriptions.get(location, f"in a beautiful {location.lower()} setting")

    # Select 3-4 amenities randomly
    selected_amenities = random.sample(amenities, min(4, len(amenities)))
    selected_features = random.sample(type_features, min(3, len(type_features)))

    # Combine amenities and features
    all_features = selected_amenities + selected_features
    random.shuffle(all_features)

    # Create feature list
    if len(all_features) >= 3:
        feature_text = f"Enjoy {all_features[0]}, {all_features[1]}, and {all_features[2]}"
        if len(all_features) > 3:
            feature_text += f", along with {', '.join(all_features[3:])}"
    else:
        feature_text = f"Experience {' and '.join(all_features)}"

    # Country-specific closing
    country_closings = {
        "USA": "making it the perfect choice for your American adventure.",
        "Canada": "providing an authentic Canadian hospitality experience.",
        "Mexico": "offering you a taste of Mexico's warm hospitality and vibrant culture."
    }

    closing = country_closings.get(country, "ensuring a memorable stay for every guest.")

    # Combine all parts
    description = f"{opening} Located {location_desc}, our property combines modern comfort with local charm. {feature_text}, {closing}"

    return description

# US States
us_states = [
    "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
    "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
    "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan",
    "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
    "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
    "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota",
    "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia",
    "Wisconsin", "Wyoming"
]

# Canadian Provinces and Territories
canadian_provinces = [
    "Alberta", "British Columbia", "Manitoba", "New Brunswick", "Newfoundland and Labrador",
    "Northwest Territories", "Nova Scotia", "Nunavut", "Ontario", "Prince Edward Island",
    "Quebec", "Saskatchewan", "Yukon"
]

# Mexican States
mexican_states = [
    "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas",
    "Chihuahua", "Coahuila", "Colima", "Durango", "Guanajuato", "Guerrero", "Hidalgo",
    "Jalisco", "México", "Michoacán", "Morelos", "Nayarit", "Nuevo León", "Oaxaca",
    "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora",
    "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz", "Yucatán", "Zacatecas"
]

# Country-state mapping
countries_states = {
    "USA": us_states,
    "Canada": canadian_provinces,
    "Mexico": mexican_states
}

countries = list(countries_states.keys())

cancellation_policies = [
    "Full refund if cancelled 48 hours prior",
    "50% if cancelled within 48 hours",
    "No penalty if cancelled 72 hours prior",
    "20% penalty if cancelled within 48 hours",
    "25% penalty if cancelled within 24 hours",
    "30% penalty if cancelled within 72 hours",
    "40% penalty if cancelled within 24 hours",
    "10% penalty if cancelled within 24 hours",
    "15% penalty if cancelled within 24 hours"
]

def unique_property_names(n):
    names = set()
    while len(names) < n:
        name = f"{fake.unique.company()} {random.choice(['Resort','Hotel','Lodge','Inn','Suites','Manor','Retreat','Cabin'])}"
        names.add(name)
    return list(names)

def unique_emails(names):
    emails = []
    for name in names:
        slug = name.lower().replace(" ", "").replace("&", "and").replace("-", "").replace(",", "")
        emails.append(f"info@{slug}.com")
    return emails

def get_country_specific_data(country):
    """Get appropriate state/province and locale data for the specified country"""
    if country == "USA":
        fake_locale = Faker('en_US')
    elif country == "Canada":
        fake_locale = Faker('en_CA')
    elif country == "Mexico":
        fake_locale = Faker('es_MX')
    else:
        fake_locale = Faker('en_US')  # fallback

    return fake_locale, countries_states[country]

property_names = unique_property_names(1000)
property_emails = unique_emails(property_names)

properties = []
for i in range(1000):
    # First select country, then get appropriate state/province
    country = random.choice(countries)
    fake_locale, states = get_country_specific_data(country)

    state = random.choice(states)
    city = fake_locale.city()
    address = fake_locale.address().replace("\n", ", ")
    phone = fake_locale.phone_number()
    location = random.choice(locations)
    airport_distance = f"{random.randint(2, 30)} miles from {fake_locale.city()} International Airport"
    price = round(random.uniform(75, 750), 2)
    commission = round(price * random.uniform(0.1, 0.2), 2)
    cancellation = random.choice(cancellation_policies)

    # Generate property description
    description = generate_property_description(property_names[i], location, country)

    properties.append({
        "id": i + 1,
        "propertyName": property_names[i],
        "propertyDescription": description,
        "propertyLocation": location,
        "propertyCity": city,
        "propertyState": state,
        "propertyCountry": country,
        "propertyAddress": address,
        "propertyPhoneNumber": phone,
        "propertyEmailAddress": property_emails[i],
        "propertyAirportProximity": airport_distance,
        "propertyPricePerNight": price,
        "propertyCommissionAmount": commission,
        "propertyCancellationPenalty": cancellation
    })

# Wrap the properties array in an object with a "properties" key
data = {
    "properties": properties
}

with open("propertyFiles.json", "w") as f:
    json.dump(data, f, indent=2)

print(f"Generated {len(properties)} properties with descriptions:")
print(f"- USA properties: {len([p for p in properties if p['propertyCountry'] == 'USA'])}")
print(f"- Canada properties: {len([p for p in properties if p['propertyCountry'] == 'Canada'])}")
print(f"- Mexico properties: {len([p for p in properties if p['propertyCountry'] == 'Mexico'])}")

# Print a sample description
print(f"\nSample description:")
print(f"Property: {properties[0]['propertyName']}")
print(f"Description: {properties[0]['propertyDescription']}")
