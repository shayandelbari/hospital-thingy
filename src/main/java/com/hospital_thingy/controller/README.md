# Controller Package

This package contains REST API endpoints.

Controllers:
- Handle HTTP requests (GET, POST, PUT, DELETE)
- Call service layer methods
- Return responses (usually JSON)

Annotations used:
- @RestController
- @RequestMapping
- @GetMapping, @PostMapping, etc.

Controllers should:
- Be thin (no business logic)
- Delegate work to services