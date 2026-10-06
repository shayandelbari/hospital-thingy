# Hospital Thingy

Hospital Thingy is a college software engineering project: a Spring Boot hospital management system built around a clean, layered Java architecture. The application models the relationships between patients, doctors, appointments, and medical records while exposing a REST API for managing that data.

This project demonstrates backend development skills including domain modeling with JPA, service-layer business logic, DTO mapping, validation, exception handling, database seeding, and automated testing.

## Highlights

- Manage patients, doctors, appointments, and medical records
- Create, retrieve, update, and delete core hospital entities
- Search patients and doctors by supported query parameters
- Filter patients by active status and doctors by speciality
- Schedule, reschedule, start, complete, cancel, and postpone appointments
- Retrieve appointments and medical records through patient, doctor, and appointment relationships
- Seed a new database with representative sample data on startup
- Return DTOs from the API to keep persistence entities separate from client-facing models
- Handle domain errors through a centralized exception handler

## Technology

- **Java 17**
- **Spring Boot 4.0.5**
- **Spring Web MVC** for REST endpoints
- **Spring Data JPA** for persistence
- **MySQL** for the runtime database
- **MapStruct** for DTO-to-entity mapping
- **Maven** for dependency management and builds
- **JUnit and Spring Boot Test** for automated tests

## Architecture

The application is organized into focused layers:

```text
src/main/java/com/hospital_thingy/
├── controller/   REST endpoints and request handling
├── service/      Business operations and workflow rules
├── repository/   Spring Data JPA persistence interfaces
├── entity/       JPA domain models and relationships
├── DTO/          API request and response models
├── mapper/       MapStruct conversion between DTOs and entities
├── exception/    Application-specific domain exceptions
├── config/       Startup configuration and database seeding
└── view/         Console startup entry point
```

Controllers remain thin and delegate business operations to services. Repositories handle persistence, while DTOs and mappers provide a boundary between the API and the database model.

## API Overview

All REST resources are served under `/api`.

| Resource | Base path | Examples |
| --- | --- | --- |
| Patients | `/api/patients` | Search, create, update, delete, view appointments and records |
| Doctors | `/api/doctors` | Search, filter by speciality, create, update, delete, view appointments |
| Appointments | `/api/appointments` | Schedule, reschedule, start, complete, cancel, postpone |
| Medical records | `/api/medical-records` | List, retrieve, and add medical records |

Example requests:

```text
GET  /api/patients?active=true
GET  /api/patients/5/appointments
GET  /api/doctors?speciality=CARDIOLOGY
GET  /api/appointments/search?doctorId=2&date=2026-04-15
PUT  /api/appointments/10/complete
```

## Getting Started

### Prerequisites

- Java 17 or newer
- MySQL
- A MySQL database and credentials available to the application

### Configure the database

The project activates the `local` Spring profile by default. Create `src/main/resources/application-local.properties` locally (this file is ignored by Git) and provide your MySQL connection settings:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_thingy
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

The default configuration updates the schema automatically and enables SQL logging:

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Run the application

Using the Maven wrapper:

```bash
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

When the database is empty, the startup seeder creates sample patients, doctors, appointments, and vital-sign medical records. To disable seeding, set:

```properties
spring.seeder.enabled=false
```

The application also prints a console startup message, while the primary application interface is the REST API.

## Testing

Run the test suite with:

```bash
./mvnw test
```

The test suite includes application context coverage and service-level tests for patient and appointment workflows.

## Academic Project

This project was developed for college coursework as a practical backend application for modeling a real-world healthcare domain. It focuses on maintainable separation of concerns, relational data modeling, API design, and predictable error handling.
