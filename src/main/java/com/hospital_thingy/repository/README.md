# Repository Package

This package contains interfaces for database access.

Repositories extend Spring Data JPA interfaces such as:
- JpaRepository

They provide:
- CRUD operations (save, find, delete)
- Query methods (custom finders)

No implementation is required — Spring generates it automatically.

Example:
- PatientRepository
- AppointmentRepository