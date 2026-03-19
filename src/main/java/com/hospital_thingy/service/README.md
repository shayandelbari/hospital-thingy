# Service Package

This package contains the business logic of the application.

Services act as a bridge between:
- Controllers (API layer)
- Repositories (data layer)

Responsibilities:
- Validate data
- Enforce business rules
- Coordinate operations between entities

Example:
- Scheduling an appointment
- Checking working hours
- Handling application logic

Services should NOT handle HTTP requests or database details directly.