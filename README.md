# Cinema Ticket Service

A Spring Boot application for managing cinema ticket transactions, including customer validation and ticket calculations.

## Table of Contents
- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Error Handling](#error-handling)
- [Run using Docker](#run-using-docker)

## Features

- RESTful API for ticket transactions
- Customer and transaction validation using Jakarta Bean Validation
- Spring Factory pattern for creating different types of ticket calculators
- Strategy pattern for ticket price calculation
- Error handling with detailed validation messages
- Sample test data and integration tests for both success and failure scenarios
- API documentation using Swagger/OpenAPI
- Containerization with Docker
- Configuration management using application.yml
-  Logging with SLF4J , Logback and MDC

## Requirements

- Java 17+
- Gradle
- (Optional) Docker for containerization

## Getting Started

### Build
To build the application, run the following command in the project root directory:

```bash
./gradlew clean build
```
### Run
To run the application locally, use:

```bash 
./gradlew bootRun
```

### API Documentation
You can access the Swagger UI for API documentation at:
```
http://localhost:8080/swagger-ui/index.html
``` 


### Sample Data
The application includes sample data for customers and transactions, which can be found in the `testData` package.

```
{
  "transactionId": 1,
  "customers": [
    {
      "name": "John Smith",
      "age": 70
    },
    {
      "name": "Jane Doe",
      "age": 5
    },
    {
      "name": "Bob Doe",
      "age": 6
    }
  ]
}
``` 

### Testing
The application includes unit and integration tests. To run the tests, use:

```bash
./gradlew test
```
## Configuration
All configurations are located in the `application.yml` file.

## Error Handling
Error responses are structured as follows:

```json
{
  "errorCode": 400,
  "errorMessage": "Detailed error message here"
}
```

## Run using Docker
To build and run the application using Docker, follow these steps:
1. Build the Docker image:
   ```bash
   docker build -t cinema-ticket-service .
   ```
2. Run the Docker container:
   ```bash
    docker run -p 8080:8080 cinema-ticket-service
    ```
