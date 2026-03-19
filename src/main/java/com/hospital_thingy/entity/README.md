# Entity Package

This package contains the core domain models of the application.

Each class represents a database table and is annotated with JPA annotations such as:
- @Entity
- @Id
- @OneToMany / @ManyToOne / @OneToOne

Examples:
- Patient
- Doctor
- Appointment
- MedicalRecord

These classes should only contain:
- Fields (data)
- Relationships
- Basic constructors/getters/setters

Business logic should NOT be placed here.