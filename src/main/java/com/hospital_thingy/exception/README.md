# Exception Package

This package contains custom exceptions and global error handling.

Includes:
- Custom exception classes (e.g., InvalidAppointmentTimeException)
- Global exception handlers (@RestControllerAdvice)

Responsibilities:
- Provide meaningful error messages
- Handle application-specific errors
- Keep error handling centralized

This improves code clarity and API reliability.

CHECKS ALL SERVICES FOR EACH CLASS NEED TO HAVE:
- Empty/missing data  
- Patient cant be deleted if it has appointments
- Doctor cant be deleted if it has appointments
- Id doesn't exist (Fk doesn't exist)
- Invalid data entered (names, data, etc) (datatypes)
