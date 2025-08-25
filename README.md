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

[Rest of the README continues as before...]