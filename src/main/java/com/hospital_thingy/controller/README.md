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

## Boilerplate Pattern

Use this structure for new controllers:

```java
@RestController
@RequestMapping("/api/example")
public class ExampleController {
	private final ExampleService exampleService;

	public ExampleController(ExampleService exampleService) {
		this.exampleService = exampleService;
	}

	@GetMapping
	public List<ExampleDTO> getAll() {
		return exampleService.getAll();
	}

	@GetMapping("/{id}")
	public ExampleDTO getById(@PathVariable Long id) {
		return exampleService.getById(id);
	}

	@GetMapping("/search")
	public ExampleDTO getByQueryParam(@RequestParam Long id) {
		return exampleService.getById(id);
	}
}
```

## Reading GET Values

Use `@RequestParam` for query parameters:

- Request: `/api/appointments/search?appointmentId=10`
- Method argument: `@RequestParam Long appointmentId`

Use `@PathVariable` for dynamic URL values:

- Request: `/api/appointments/10`
- Method argument: `@PathVariable Long id`

Rule of thumb:

- Use query parameters for filters, optional values, or search criteria.
- Use path variables for resource identity, like IDs.
