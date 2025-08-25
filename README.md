# Spring Boot Property Loader

A high-performance Spring Boot application that loads property data from JSON files into a MySQL database using virtual threads for concurrent processing.

**Author:** Carey Lotzer

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Design Patterns](#design-patterns)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Configuration](#configuration)
- [Usage](#usage)
- [Data Generation](#data-generation)
- [API Endpoints](#api-endpoints)
- [Performance](#performance)
- [Troubleshooting](#troubleshooting)
- [Development](#development)
- [Contributing](#contributing)

## Overview

This Spring Boot application is designed to efficiently load large datasets of property information from JSON files into a MySQL database. It features multi-threaded processing using Java 17+ virtual threads, transactional data integrity, and a REST API for data access.

### Key Capabilities

- **Bulk Data Loading**: Loads property data from JSON files into MySQL database
- **Multi-threaded Processing**: Uses Java virtual threads for concurrent database operations
- **Configurable Concurrency**: Adjustable thread count via application properties
- **Data Validation**: Robust error handling and data validation during import
- **REST API**: Provides endpoints to query loaded property data
- **Performance Monitoring**: Built-in timing and progress reporting

## Features

- ✅ **Fast Data Loading**: Multi-threaded property insertion with configurable concurrency
- ✅ **Virtual Threads**: Uses Java 17+ virtual threads for lightweight concurrency
- ✅ **Transaction Management**: Each property save is wrapped in its own transaction
- ✅ **Error Handling**: Comprehensive error logging and graceful failure handling
- ✅ **Data Generation**: Python script to generate test data with realistic property information
- ✅ **REST API**: Query endpoints for accessing loaded property data
- ✅ **Configurable**: Easy configuration via application.properties
- ✅ **Development Tools**: Spring Boot DevTools for hot reloading during development

## Technology Stack

- **Java**: 17 (requires Java 17+ for virtual threads and Spring Boot 3.x compatibility)
- **Spring Boot**: 3.5.5
- **Spring Data JPA**: For database operations
- **Hibernate**: ORM for entity management
- **MySQL**: Primary database
- **Jackson**: JSON parsing and processing
- **Maven**: Build tool and dependency management
- **Python**: For test data generation (Faker library)

## Design Patterns

This application implements several well-established design patterns to ensure maintainability, scalability, and clean architecture:

### 🏗️ **Architectural Patterns**

#### **Layered Architecture (N-Tier)**
- **Controller Layer** (`PropertyController.java`): Handles HTTP requests and responses
- **Service Layer** (`PropertyService.java`): Contains business logic and transaction management
- **Repository Layer** (`PropertyRepository.java`): Data access abstraction
- **Entity Layer** (`Property.java`): Domain model representation

*Benefits: Clear separation of concerns, easier testing, maintainable codebase*

#### **Model-View-Controller (MVC)**
- **Model**: `Property.java` entity represents the data structure
- **View**: REST API JSON responses (no traditional view layer)
- **Controller**: `PropertyController.java` handles HTTP requests

*Benefits: Separation of presentation logic from business logic*

### 🔧 **Creational Patterns**

#### **Dependency Injection (IoC Container)**
```java
@Autowired
private PropertyService propertyService;

@Autowired
private PropertyRepository propertyRepository;
```
- Spring's IoC container manages object creation and lifecycle
- Constructor injection and field injection used throughout

*Benefits: Loose coupling, easier testing, better maintainability*

#### **Factory Pattern (Implicit)**
- Spring Boot auto-configuration acts as factories for beans
- `@Configuration` classes serve as factory methods for bean creation
- JPA repositories are created by Spring Data factory mechanisms

*Benefits: Centralized object creation, consistent configuration*

### 🎯 **Behavioral Patterns**

#### **Command Pattern**
- `DataLoader.java` implements `CommandLineRunner` interface
- Encapsulates the data loading operation as a command object
- Executed at application startup

```java
@Component
public class DataLoader implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        // Command execution logic
    }
}
```

*Benefits: Encapsulates requests, allows parameterization, supports queuing*

#### **Template Method Pattern**
- Spring Boot's `CommandLineRunner` defines the template for startup execution
- Spring Data JPA repositories follow template method pattern for CRUD operations
- Transaction management follows AOP template pattern

*Benefits: Code reuse, consistent algorithm structure*

#### **Observer Pattern**
- Spring's event-driven architecture
- Application lifecycle events and listeners
- JPA entity lifecycle callbacks

*Benefits: Loose coupling between event producers and consumers*

### 🏛️ **Structural Patterns**

#### **Repository Pattern**
```java
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Data access methods
}
```
- Abstracts data access logic
- Provides a uniform interface for data operations
- Hides database implementation details

*Benefits: Testability, flexibility in data source changes, clean separation*

#### **Facade Pattern**
- `PropertyService.java` acts as a facade for complex database operations
- Simplifies the interface for property management operations
- Hides the complexity of transaction management and error handling

```java
@Service
@Transactional
public class PropertyService {
    public void saveProperty(Property property) {
        // Simplified interface hiding complexity
    }
}
```

*Benefits: Simplified interface, reduced complexity for clients*

#### **Adapter Pattern**
- Jackson JSON library adapts JSON data to Java objects
- JPA adapts object-oriented code to relational database operations
- Spring Boot adapts various frameworks into a cohesive application

*Benefits: Integration of incompatible interfaces, code reusability*

### 🔄 **Concurrency Patterns**

#### **Producer-Consumer Pattern**
- JSON parser produces Property objects
- Virtual threads consume and save properties to database
- Blocking queue implicitly manages the flow

*Benefits: Efficient resource utilization, decoupled processing*

#### **Thread Pool Pattern**
- Java Virtual Threads provide lightweight thread pool management
- Configurable concurrency via `property.loader.concurrent-threads`
- Automatic thread lifecycle management

```java
// Virtual threads configuration
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    // Concurrent processing
}
```

*Benefits: Resource efficiency, scalability, performance optimization*

### 🎭 **Enterprise Patterns**

#### **Data Transfer Object (DTO)**
- `Property.java` serves as both entity and DTO
- JSON structure maps directly to property objects
- RESTful API responses use the entity as DTO

*Benefits: Reduced network calls, data encapsulation*

#### **Unit of Work**
- Spring's `@Transactional` annotation implements Unit of Work pattern
- Groups related database operations into atomic transactions
- Automatic rollback on exceptions

```java
@Transactional
public void saveProperty(Property property) {
    // All operations in single transaction
}
```

*Benefits: Data consistency, atomic operations, simplified error handling*

#### **Active Record Pattern**
- JPA entities combine data and behavior
- Entities are aware of their persistence mechanism
- Built-in CRUD operations through repository inheritance

*Benefits: Simplified data access, object-relational mapping*

### 🔌 **Integration Patterns**

#### **Configuration Pattern**
- `application.properties` centralizes configuration
- Environment-specific property overrides
- Type-safe configuration binding with `@Value` annotations

*Benefits: Externalized configuration, environment flexibility*

#### **Resource Management Pattern**
- Try-with-resources for file handling and executor management
- Automatic resource cleanup and connection management
- Spring Boot's auto-configuration handles resource lifecycle

*Benefits: Proper resource cleanup, memory leak prevention*

### 📊 **Pattern Benefits Summary**

| Pattern Category | Primary Benefits | Used In |
|------------------|------------------|---------|
| **Architectural** | Separation of concerns, maintainability | Layered structure, MVC |
| **Creational** | Loose coupling, testability | Dependency injection, factories |
| **Behavioral** | Flexibility, reusability | Command, template method, observer |
| **Structural** | Simplified interfaces, adaptability | Repository, facade, adapter |
| **Concurrency** | Performance, scalability | Producer-consumer, thread pool |
| **Enterprise** | Data integrity, consistency | DTO, unit of work, active record |
| **Integration** | Configuration management, resource safety | Configuration, resource management |

### 🚀 **Pattern Synergy**

The combination of these patterns creates a robust, scalable, and maintainable application:

1. **Layered Architecture + Dependency Injection** = Clean, testable code structure
2. **Repository + Unit of Work** = Consistent, transactional data access
3. **Command + Producer-Consumer** = Efficient batch processing
4. **Facade + Configuration** = Simple, flexible API design
5. **Virtual Threads + Thread Pool** = High-performance concurrent processing

This pattern-rich architecture ensures the application can handle large datasets efficiently while remaining easy to extend and maintain.

## Prerequisites

### Required Software

1. **Java Development Kit (JDK) 17+**
   ```bash
   java -version
   # Should show version 17 or higher
   ```

2. **MySQL Server 8.0+**
   ```bash
   mysql --version
   # Should show MySQL 8.0 or higher
   ```

3. **Maven 3.8+**
   ```bash
   mvn -version
   # Should show Maven 3.8 or higher
   ```

4. **Python 3.8+ (for data generation)**
   ```bash
   python3 --version
   # Should show Python 3.8 or higher
   ```

### System Requirements

- **Memory**: Minimum 4GB RAM, 8GB recommended for large datasets
- **Storage**: At least 1GB free space for application and data files
- **Network**: Internet connection for Maven dependencies and MySQL connection

## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/clotzer/spring-boot-property-loader.git
cd spring-boot-property-loader
```

### 2. Database Setup

Create a MySQL database and user:

```sql
-- Connect to MySQL as root
mysql -u root -p

-- Create database
CREATE DATABASE property_db;

-- Create user (replace 'password' with your preferred password)
CREATE USER 'property_user'@'localhost' IDENTIFIED BY 'password';

-- Grant privileges
GRANT ALL PRIVILEGES ON property_db.* TO 'property_user'@'localhost';
FLUSH PRIVILEGES;

-- Exit MySQL
EXIT;
```

### 3. Configure Application Properties

Create or update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/property_db
spring.datasource.username=property_user
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.format_sql=true

# Application Configuration
property.loader.concurrent-threads=10
property.loader.file-path=src/main/resources/propertyFiles.json

# Logging Configuration
logging.level.com.clotzer.property=DEBUG
logging.level.org.springframework.transaction=DEBUG
```

### 4. Install Python Dependencies (for data generation)

```bash
pip install faker
```

### 5. Build the Application

```bash
mvn clean compile
```

## Configuration

### Application Properties

| Property | Description | Default | Example |
|----------|-------------|---------|---------|
| `property.loader.concurrent-threads` | Number of virtual threads for concurrent processing | 10 | 20 |
| `property.loader.file-path` | Path to JSON file containing property data | `src/main/resources/propertyFiles.json` | `/path/to/data.json` |
| `spring.datasource.url` | MySQL database URL | - | `jdbc:mysql://localhost:3306/property_db` |
| `spring.datasource.username` | Database username | - | `property_user` |
| `spring.datasource.password` | Database password | - | `password` |

### Environment-Specific Configuration

Create environment-specific property files:

- `application-dev.properties` (Development)
- `application-prod.properties` (Production)
- `application-test.properties` (Testing)

Run with specific profile:
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

## Usage

### Running the Application

#### Option 1: Using Maven

```bash
# Run with default settings
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring.profiles.active=dev

# Run with custom properties
mvn spring-boot:run -Dproperty.loader.concurrent-threads=20
```

#### Option 2: Using JAR

```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/property-0.0.1-SNAPSHOT.jar

# Run with custom properties
java -jar target/property-0.0.1-SNAPSHOT.jar --property.loader.concurrent-threads=20
```

#### Option 3: Using IDE

- Import project into IntelliJ IDEA or Eclipse
- Run the `PropertyApplication.java` main class
- Configure run parameters in IDE settings

### Application Startup Process

1. **Spring Boot Initialization**: Application context loads, beans are created
2. **Database Connection**: Establishes connection pool to MySQL
3. **Schema Creation**: Hibernate creates/updates database schema if needed
4. **Data Loading**: `DataLoader` component executes automatically
5. **JSON Parsing**: Reads and parses property data from JSON file
6. **Concurrent Processing**: Virtual threads process and save properties
7. **REST API**: Web server starts and endpoints become available

### Monitoring Progress

The application provides detailed logging during startup:

```
INFO  - Starting property data loading...
INFO  - Found 10000 properties to load
INFO  - Using 10 concurrent threads for processing
INFO  - Loaded 1000 properties (10% complete)
INFO  - Loaded 2000 properties (20% complete)
...
INFO  - Property loading completed successfully in 45.2 seconds
INFO  - Total properties saved: 10000
```

## Data Generation

### Generate Test Data

Use the included Python script to generate realistic property data:

```bash
# Navigate to resources directory
cd src/main/resources/

# Generate default dataset (1000 properties)
python3 generate_property_files.py

# Generate custom dataset
python3 generate_property_files.py --count 5000 --output custom_properties.json
```

### Python Script Options

```bash
# Generate 10,000 properties
python3 generate_property_files.py --count 10000

# Custom output file
python3 generate_property_files.py --output /path/to/output.json

# Include additional property types
python3 generate_property_files.py --types residential,commercial,industrial

# Generate with seed for reproducible data
python3 generate_property_files.py --seed 12345
```

### Generated Data Structure

```json
{
  "properties": [
    {
      "address": "123 Main Street",
      "city": "Springfield",
      "state": "Illinois",
      "zipCode": "62701",
      "propertyType": "residential",
      "bedrooms": 3,
      "bathrooms": 2,
      "squareFootage": 1500,
      "lotSize": 0.25,
      "yearBuilt": 1995,
      "price": 250000.00,
      "description": "Beautiful family home with modern amenities"
    }
  ]
}
```

## API Endpoints

### Property Management

#### Get All Properties
```http
GET /api/properties
```

**Response:**
```json
[
  {
    "id": 1,
    "address": "123 Main Street",
    "city": "Springfield",
    "state": "Illinois",
    "zipCode": "62701",
    "propertyType": "residential",
    "bedrooms": 3,
    "bathrooms": 2,
    "squareFootage": 1500,
    "lotSize": 0.25,
    "yearBuilt": 1995,
    "price": 250000.00,
    "description": "Beautiful family home with modern amenities"
  }
]
```

#### Get Property by ID
```http
GET /api/properties/{id}
```

**Example:**
```bash
curl http://localhost:8080/api/properties/1
```

#### Get Properties by City
```http
GET /api/properties/city/{city}
```

**Example:**
```bash
curl http://localhost:8080/api/properties/city/Springfield
```

#### Get Properties by State
```http
GET /api/properties/state/{state}
```

**Example:**
```bash
curl http://localhost:8080/api/properties/state/Illinois
```

#### Get Properties by Type
```http
GET /api/properties/type/{type}
```

**Example:**
```bash
curl http://localhost:8080/api/properties/type/residential
```

### Health and Monitoring

#### Application Health
```http
GET /actuator/health
```

#### Application Info
```http
GET /actuator/info
```

### Error Responses

```json
{
  "timestamp": "2025-08-25T16:41:02.123+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Property not found with id: 999",
  "path": "/api/properties/999"
}
```

## Performance

### Benchmark Results

Test Environment:
- **Hardware**: Intel i7-8650U, 16GB RAM, SSD
- **Database**: MySQL 8.0 local instance
- **Dataset**: 10,000 properties
- **Configuration**: 10 concurrent virtual threads

| Metric | Value |
|--------|-------|
| **Total Loading Time** | 45.2 seconds |
| **Properties per Second** | ~221 |
| **Peak Memory Usage** | 256MB |
| **Database Connections** | 10 (pooled) |
| **CPU Usage** | 15-25% |

### Performance Tuning

#### Thread Configuration
```properties
# Increase for CPU-intensive workloads
property.loader.concurrent-threads=20

# Decrease for memory-constrained environments
property.loader.concurrent-threads=5
```

#### Database Optimization
```properties
# Connection pool settings
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.connection-timeout=30000

# JPA batch processing
spring.jpa.properties.hibernate.jdbc.batch_size=25
spring.jpa.properties.hibernate.order_inserts=true
spring.jpa.properties.hibernate.order_updates=true
```

#### Memory Settings
```bash
# Increase heap size for large datasets
java -Xmx2g -Xms1g -jar property-loader.jar

# Enable G1 garbage collector for better throughput
java -XX:+UseG1GC -jar property-loader.jar
```

### Monitoring Performance

#### JVM Metrics
```bash
# Enable JMX monitoring
java -Dcom.sun.management.jmxremote -jar property-loader.jar

# Use JConsole or VisualVM to monitor
jconsole
```

#### Application Metrics
```properties
# Enable Actuator endpoints
management.endpoints.web.exposure.include=health,info,metrics,prometheus

# Custom metrics endpoint
management.endpoint.metrics.enabled=true
```

## Troubleshooting

### Common Issues

#### 1. Database Connection Errors

**Error:** `Communications link failure`

**Solutions:**
```bash
# Check MySQL service status
sudo systemctl status mysql

# Restart MySQL service
sudo systemctl restart mysql

# Verify connection manually
mysql -u property_user -p -h localhost property_db
```

#### 2. Out of Memory Errors

**Error:** `java.lang.OutOfMemoryError: Java heap space`

**Solutions:**
```bash
# Increase heap size
java -Xmx4g -jar property-loader.jar

# Reduce concurrent threads
property.loader.concurrent-threads=5

# Enable verbose GC logging
java -XX:+PrintGC -XX:+PrintGCDetails -jar property-loader.jar
```

#### 3. JSON Parsing Errors

**Error:** `JsonParseException: Unexpected character`

**Solutions:**
- Validate JSON file format using online JSON validator
- Check file encoding (should be UTF-8)
- Verify file path in application.properties
- Regenerate JSON file using Python script

```bash
# Validate JSON file
python3 -m json.tool src/main/resources/propertyFiles.json
```

#### 4. Port Already in Use

**Error:** `Port 8080 was already in use`

**Solutions:**
```bash
# Find process using port 8080
lsof -i :8080

# Kill the process (replace PID)
kill -9 <PID>

# Use different port
java -jar property-loader.jar --server.port=8081
```

#### 5. Maven Build Failures

**Error:** `Failed to execute goal`

**Solutions:**
```bash
# Clean and rebuild
mvn clean install

# Skip tests if needed
mvn clean install -DskipTests

# Update dependencies
mvn dependency:resolve

# Clear local repository cache
rm -rf ~/.m2/repository
mvn clean install
```

### Debug Mode

Enable debug logging for troubleshooting:

```properties
# Enable debug logging
logging.level.com.clotzer.property=DEBUG
logging.level.org.springframework.transaction=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

# Log to file
logging.file.name=application.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} - %msg%n
```

### Performance Issues

#### Slow Database Inserts
- Increase batch size in Hibernate configuration
- Add database indexes on frequently queried columns
- Consider using bulk insert operations

#### High Memory Usage
- Reduce concurrent thread count
- Implement pagination for large datasets
- Enable JVM garbage collection tuning

#### Connection Pool Exhaustion
- Increase maximum pool size
- Reduce connection timeout
- Monitor connection leaks with logging

## Development

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── clotzer/
│   │           └── property/
│   │               ├── PropertyApplication.java
│   │               ├── controller/
│   │               │   └── PropertyController.java
│   │               ├── service/
│   │               │   └── PropertyService.java
│   │               ├── repository/
│   │               │   └── PropertyRepository.java
│   │               ├── entity/
│   │               │   └── Property.java
│   │               └── config/
│   │                   └── DataLoader.java
│   └── resources/
│       ├── application.properties
│       ├── generate_property_files.py
│       └── propertyFiles.json
└── test/
    └── java/
        └── com/
            └── clotzer/
                └── property/
                    └── PropertyApplicationTests.java
```

### Setting Up Development Environment

#### IntelliJ IDEA
1. Import project as Maven project
2. Set Project SDK to Java 17+
3. Enable annotation processing
4. Configure code style and formatting
5. Install Spring Boot plugin

#### Eclipse
1. Import as existing Maven project
2. Configure Java Build Path
3. Install Spring Tools Suite (STS)
4. Configure Maven integration

#### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
3. Configure Java runtime
4. Set up debugging configuration

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=PropertyServiceTest

# Run with coverage
mvn test jacoco:report

# Run integration tests
mvn integration-test
```

### Code Quality

#### Checkstyle
```bash
# Run checkstyle
mvn checkstyle:check

# Generate checkstyle report
mvn site checkstyle:checkstyle
```

#### SonarQube Integration
```bash
# Run SonarQube analysis
mvn sonar:sonar -Dsonar.projectKey=property-loader
```

### Adding New Features

#### Creating New Entity
1. Create entity class in `entity` package
2. Add corresponding repository interface
3. Update service layer for business logic
4. Add controller endpoints
5. Write unit and integration tests

#### Adding New Endpoints
1. Add method to controller class
2. Implement service logic
3. Add validation and error handling
4. Document API in README
5. Write endpoint tests

## Contributing

We welcome contributions to improve the Spring Boot Property Loader! Here's how you can help:

### Getting Started

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes**
4. **Add tests for new functionality**
5. **Ensure all tests pass**
   ```bash
   mvn test
   ```
6. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```
7. **Push to your branch**
   ```bash
   git push origin feature/amazing-feature
   ```
8. **Open a Pull Request**

### Development Guidelines

#### Code Style
- Follow Java naming conventions
- Use meaningful variable and method names
- Add JavaDoc comments for public methods
- Keep methods focused and concise
- Follow SOLID principles

#### Testing
- Write unit tests for all new functionality
- Maintain at least 80% code coverage
- Include integration tests for critical paths
- Test error conditions and edge cases

#### Documentation
- Update README.md for new features
- Add inline code comments for complex logic
- Document API endpoints with examples
- Update configuration documentation

### Contribution Areas

We're particularly interested in contributions for:

- 🚀 **Performance Improvements**: Optimize loading algorithms, database operations
- 🔧 **New Features**: Additional data formats, export capabilities, data validation
- 📊 **Monitoring**: Enhanced metrics, health checks, performance dashboards
- 🔒 **Security**: Authentication, authorization, input validation
- 📚 **Documentation**: Tutorials, examples, deployment guides
- 🧪 **Testing**: Unit tests, integration tests, performance tests
- 🐛 **Bug Fixes**: Error handling, edge cases, compatibility issues

### Pull Request Guidelines

- Include descriptive title and detailed description
- Reference related issues using `#issue-number`
- Include screenshots for UI changes
- Ensure CI/CD pipeline passes
- Request review from maintainers
- Be responsive to feedback and suggestions

### Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Help others learn and grow
- Follow project guidelines and standards

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- **Spring Boot Team** for the excellent framework
- **Java Virtual Threads** for enabling lightweight concurrency
- **MySQL** for reliable database performance
- **Faker Library** for realistic test data generation
- **Maven** for build automation and dependency management

---

**Project Repository**: https://github.com/clotzer/spring-boot-property-loader

**Author**: Carey Lotzer ([@clotzer](https://github.com/clotzer))

**Last Updated**: August 25, 2025