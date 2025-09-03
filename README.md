# hex-spring

A small Spring Boot application built with Hexagonal Architecture (Ports & Adapters). It exposes a simple Task CRUD over HTTP and demonstrates calling an external service to enrich a task with additional user information.

## Overview
- Language/Build: Java 21, Gradle Wrapper
- Frameworks: Spring Boot 3, Spring Web, Spring Data JPA
- Database: PostgreSQL
- Architecture: Domain (models + ports) → Application (use cases + service) → Infrastructure (web/JPA/external adapters)

### Modules (high level)
- Domain
  - Models: `Task`, `AdditionalTaskInfo`
  - Ports (in): `CreateTaskUseCase`, `RetrieveTaskUseCase`, `UpdateTaskUseCase`, `DeleteTaskUseCase`, `GetAdditionalTaskInfoUseCase`
  - Ports (out): `TaskRepositoryPort`, `ExternalServicePort`
- Application
  - Use case implementations and `TaskService` orchestration
- Infrastructure
  - REST controller (`/api/tasks`)
  - JPA persistence (`TaskEntity`, Spring Data repository + adapter)
  - External API adapter (uses JsonPlaceholder to resolve user info for a given task id)

## Requirements
- JDK 21+
- PostgreSQL 14+ (or compatible)
- Network access (for the external user info call via JsonPlaceholder)

## Configuration
Default configuration is in `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/hex
spring.datasource.username=hex
spring.datasource.password=hex
spring.jpa.hibernate.ddl-auto=update
```

Adjust as needed for your environment.

## Create PostgreSQL Database and User
Below are standard psql commands to create the database and user expected by the default configuration.

1) Connect to Postgres as an admin user (e.g., `postgres`):

```
psql -U postgres
```

2) Create role/user and database:

```
-- Create user with password
CREATE ROLE hex WITH LOGIN PASSWORD 'hex';

-- Create database owned by the new user
CREATE DATABASE hex OWNER hex;

-- (Optional) Ensure privileges
GRANT ALL PRIVILEGES ON DATABASE hex TO hex;
```

3) Verify connection from your shell:

```
psql "postgresql://hex:hex@localhost:5432/hex"
```

Optional: Quickstart with Docker

```
docker run --name hex-postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16
# Then inside the container or via psql on host, run the SQL above to create user/db.
```

## Build and Run
- Run (dev):

```
./gradlew bootRun
```

- Build JAR and run:

```
./gradlew build
java -jar build/libs/hex-spring-0.0.1-SNAPSHOT.jar
```

## API Endpoints
Base path: `/api/tasks`

- POST `/api/tasks` — create a task
- GET `/api/tasks/{taskId}` — get a task by id
- GET `/api/tasks` — list all tasks
- PUT `/api/tasks/{taskId}` — update a task
- DELETE `/api/tasks/{taskId}` — delete a task
- GET `/api/tasks/{taskId}/additionalInfo` — fetch additional user info from external API

### Data Models
Task (request/response)

```
{
  "id": 1,
  "title": "Write docs",
  "description": "Add README and examples",
  "creationDate": "2025-01-01T10:00:00",
  "completed": false
}
```

AdditionalTaskInfo (response)

```
{
  "userId": 1,
  "userName": "Leanne Graham",
  "userEmail": "leanne@example.com"
}
```

Note: `creationDate` uses ISO-8601 local date-time.

## cURL Examples
Base URL: `http://localhost:8080`

- Create a task

```
curl -sS -X POST http://localhost:8080/api/tasks \
  -H 'Content-Type: application/json' \
  -d '{
    "id": 1,
    "title": "Write docs",
    "description": "Add README and examples",
    "creationDate": "2025-01-01T10:00:00",
    "completed": false
  }'
```

- Get a task by id

```
curl -sS http://localhost:8080/api/tasks/1
```

- List all tasks

```
curl -sS http://localhost:8080/api/tasks
```

- Update a task (id in body should match path id)

```
curl -sS -X PUT http://localhost:8080/api/tasks/1 \
  -H 'Content-Type: application/json' \
  -d '{
    "id": 1,
    "title": "Write better docs",
    "description": "Tighten examples",
    "creationDate": "2025-01-01T10:00:00",
    "completed": true
  }'
```

- Delete a task

```
curl -sS -X DELETE http://localhost:8080/api/tasks/1
```

- Get additional task info (uses JsonPlaceholder). For best results, use an id that exists on JsonPlaceholder (e.g., 1..200):

```
curl -sS http://localhost:8080/api/tasks/1/additionalInfo
```

## Notes
- The additional info endpoint does not check the local DB entry; it directly consults the external API using the provided `taskId`.
- Hibernate `ddl-auto=update` will manage schema evolution in dev. For production, consider migrations (Flyway/Liquibase).

