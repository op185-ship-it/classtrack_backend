# ClassTrack Backend (Spring Boot)

Backend service for the ClassTrack final-year project. Built with Spring Boot, Spring WebMVC, Spring Data JPA, PostgreSQL, and Springdoc OpenAPI (Swagger UI).

## Tech stack

- Java + Spring Boot
- Spring WebMVC (REST APIs)
- Spring Data JPA (ORM)
- PostgreSQL (database)
- Springdoc OpenAPI UI (Swagger)

## Prerequisites

- Java **25** (project is configured with `java.version=25` in `pom.xml`)
- PostgreSQL (local) **or** Docker (recommended for local DB)

## Configuration (Database + Flask service)

This project reads configuration from `src/main/resources/application.properties` using environment variables:

- `DB_URL` (default: `jdbc:postgresql://localhost:5432/class_track_db`)
- `DB_USERNAME` (default: `postgres`)
- `DB_PASSWORD` (default: empty)
- `FLASK_SERVICE_URL` (default: `http://localhost:5000`)

Create a `.env` file (not committed) based on `.env.example` and export the variables in your shell, or set them in your IDE run configuration.

## Run PostgreSQL (Docker)

Start a local Postgres instance:

```bash
docker compose up -d
```

## Run the application

```bash
./mvnw spring-boot:run
```

The app runs on port `8080` by default.

## Troubleshooting

### `FATAL: password authentication failed for user "postgres"`

Your local PostgreSQL password does not match what the app is using.

- Option A (recommended): set env vars before running:

```bash
export DB_PASSWORD="<your postgres password>"
./mvnw spring-boot:run
```

- Option B: use a local-only Spring profile file:
  1) Copy `src/main/resources/application-local.properties.example` to `src/main/resources/application-local.properties`
  2) Put your real password in that local file
  3) Run: `SPRING_PROFILES_ACTIVE=local ./mvnw spring-boot:run`

## API docs (Swagger UI)

After starting the app:

- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Common API base paths

Most controllers are under:

- `/api`
- `/api/sessions`
- `/api/students`

## Upload to GitHub

1) Make sure you are **not committing secrets** (DB passwords, API keys). This repo is set up to read DB credentials via env vars (`DB_PASSWORD`, etc.).

2) Initialize git (if needed) and commit:

```bash
git init
git add .
git commit -m "Initial commit"
```

3) Create a new repository on GitHub, then add the remote and push:

```bash
git branch -M main
git remote add origin <YOUR_GITHUB_REPO_URL>
git push -u origin main
```

## Notes

- `spring.jpa.hibernate.ddl-auto=update` is enabled, so Hibernate will update tables automatically based on entities.
- File uploads are configured with very large/unlimited multipart limits in `application.properties`.
