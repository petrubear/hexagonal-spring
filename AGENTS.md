# AGENTS.md

Guidelines for using agent tooling (Codex CLI or similar) to work effectively in this repository.

## Repository Snapshot
- Language/Build: Java 21, Gradle Wrapper
- Frameworks: Spring Boot 3 (Web, Data JPA)
- Database: PostgreSQL
- Architecture: Hexagonal (Ports & Adapters)
  - Domain (models + ports)
  - Application (use case implementations + `TaskService`)
  - Infrastructure (REST controller, JPA adapter, external API adapter)

Refer to `README.md` for a user-facing overview, setup, and cURL examples.

## Agent Operating Rules
- Make code changes with `apply_patch` only. Do not attempt to edit files via shell editors.
- Prefer fast, bounded reads:
  - List files: `rg --files -n --hidden -g '!.git'`
  - Read files in chunks: `sed -n '1,200p' <file>` or `nl -ba <file> | sed -n '1,200p'`
  - Keep to ≤250 lines per read chunk to avoid truncation.
- Keep changes minimal and focused on the task. Avoid unrelated refactors.
- Follow existing code style (Java naming, package structure under `emg.demo.hex.*`).
- Update docs when changing behavior or APIs (e.g., `README.md`).
- Do not `git commit` or create branches unless explicitly asked.

## Sandboxing & Approvals
- Filesystem: workspace-write (edit files in repo only).
- Network: often restricted; request escalation for dependency downloads or external HTTP calls.
- Approvals: on-request. Ask for escalated execution when:
  - Running Gradle tasks that download dependencies: `./gradlew build`, `./gradlew bootRun`.
  - Any command that needs network or writes outside the workspace.
  - Potentially destructive actions (e.g., `rm -rf`, `git reset`).

When requesting escalations via the shell tool, include a one-sentence justification.

## Common Workflows

### Explore
- Scan structure and key files:
  - `rg --files -n --hidden -g '!.git' | sed -n '1,200p'`
  - Open build: `build.gradle`, `settings.gradle`, `gradle.properties`
  - App entry: `src/main/java/emg/demo/hex/HexSpringApplication.java`
  - Config: `src/main/resources/application.properties`

### Implement a Use Case
1) Define or update domain port in `src/main/java/emg/demo/hex/domain/ports`.
2) Implement in `application/usecases` and expose via `TaskService` as needed.
3) Wire beans in `infrastructure/config/ApplicationConfig.java`.
4) If HTTP-facing, add/modify controller in `infrastructure/controllers`.

### Persistence
- Entity: `infrastructure/entities/TaskEntity` with mapping helpers
  - `fromDomain(Task)` and `toDomain(TaskEntity)`.
- Repository adapter implements `TaskRepositoryPort` in `infrastructure/repositories` and bridges Spring Data JPA (`JpaTaskRepository`).

### External Calls
- `infrastructure/adapters/ExternalServiceAdapter` uses `RestTemplate` to call JsonPlaceholder.
- For tests, prefer mocking the port (`ExternalServicePort`) or the HTTP layer.

## Validate Changes
- Build/compile:
  - `./gradlew build` (requires network for dependencies)
- Run locally:
  - Ensure PostgreSQL is available per `README.md`.
  - `./gradlew bootRun`
- Smoke test with cURL (examples in `README.md`).

If network is blocked, skip external calls or mock them for unit tests.

## Testing Guidance
- Test framework: Spring Boot Starter Test (JUnit 5).
- Suggested patterns:
  - Unit tests for use cases by mocking ports.
  - Slice tests for controllers using `@WebMvcTest` with mocked `TaskService` or ports.
  - Persistence tests with an embedded DB, Testcontainers, or a dedicated profile.

Note: The project currently has no test sources. Add under `src/test/java/...` if needed for your task.

## Code Style & Conventions
- Packages under `emg.demo.hex` with subpackages: `domain`, `application`, `infrastructure`.
- Class naming: `*UseCase`, `*UseCaseImpl`, `*Adapter`, `*RepositoryAdapter`.
- Keep DTOs aligned with records in domain where useful. Map at boundaries (entity ↔ domain).
- Validate IDs on update paths (e.g., request path vs body). Prefer `Objects.equals(a, b)` for boxed types.

## Troubleshooting
- Build fails on dependency resolution: needs network; request escalated run for Gradle.
- DB connection errors: verify database/user/password and that Postgres is listening (see `README.md`).
- External API failures: JsonPlaceholder may be rate-limited/unavailable; handle nulls defensively or mock in tests.

## Future Enhancements (nice-to-have)
- Dev profile with H2 or Testcontainers for Postgres.
- Docker Compose for app + Postgres.
- Replace `RestTemplate` with `WebClient` and add timeouts/retries.
- Introduce DTOs at the web layer to decouple domain from HTTP payloads.
- Add Flyway/Liquibase for schema migrations.

---
This document targets agents and contributors operating via automated tooling. For end users, see `README.md`.

