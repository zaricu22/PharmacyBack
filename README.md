# Pharmacy Backend

[![Java](https://img.shields.io/badge/Java-17-blue?logo=openjdk)](https://openjdk.org/projects/jdk/17/)

A Spring Boot 3.2 REST API for managing a pharmacy — products, manufacturers, and user authentication with JWT. Built with a multi-module **Onion Architecture** separating domain, application, persistence, security, and web layers.

---

## Live Demo & Frontend

Live Demo: [https://zaricu22.github.io/PharmacyFront/](https://zaricu22.github.io/PharmacyFront/)

Source: [https://github.com/zaricu22/PharmacyFront](https://github.com/zaricu22/PharmacyFront)

---

## Tech Stack

| | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2.2 |
| Persistence | Spring Data JPA + PostgreSQL (CockroachDB in production) |
| Security | Spring Security + JWT (HS256) |
| Mapping | MapStruct |
| Build | Maven (multi-module) |
| Containerization | Docker |
| Deploy | Render |

---

## Database

Schema and seed data scripts are in `docs/db/`:

| File | Purpose |
|------|---------|
| `pharmacy_init.sql` | Creates the `pharmacy` database, `public` schema, and all tables |
| `pharmacy_seed-data.sql` | Sample users (BCrypt passwords), tokens, manufacturers, and products |

Run `pharmacy_init.sql` first (as superuser to create the database), then `pharmacy_seed-data.sql`.

> **Seed users**
>
> | Username | Password | Role |
> |----------|----------|------|
> | `user1` | `password` | USER |
> | `admin1` | `password` | ADMIN |

---

## Getting Started

### Prerequisites

- **Java 17+** — `java --version`
- **Maven** — or use the included `./mvnw` wrapper

### Clone

```bash
git clone https://github.com/zaricu22/PharmacyBack.git
cd PharmacyBack
```

### Environment Variables

| Variable | Description | Example |
|---|---|---|
| `DB_URL` | PostgreSQL JDBC connection URL | `jdbc:postgresql://localhost:5432/pharmacy` |
| `DB_USERNAME` | Database username | `admin` |
| `DB_PASSWORD` | Database password | `secret` |

### Run locally

```bash
mvn spring-boot:run -pl external/app-main
```

The application starts on `http://localhost:8085/PharmacyRest`.

---

## Deployment

Render is configured via the dashboard: Branch `main`, Dockerfile Path `./Dockerfile`, Auto-Deploy: `On Commit`.

When a new commit is pushed to `main`, Render detects it, runs `docker build` using the Dockerfile, and redeploys the service.

The Dockerfile performs a full Maven build inside the container:
- **Dockerfile** — builds the JAR from source using `maven:3.9-eclipse-temurin-17-alpine` and runs it on a slim JRE image
- The executable JAR is produced by the `app-main` module and output to the root `target/` directory

---

## API Endpoints — `/PharmacyRest/api/v1`

### Auth

| Method | Path | Auth required | Description |
|--------|------|---------------|-------------|
| `POST` | `/api/v1/register` | None | Register a new user, returns JWT |
| `POST` | `/api/v1/authenticate` | None | Login, returns JWT + refresh token |
| `POST` | `/api/v1/refresh-token` | Refresh token | Issue a new access token |
| `POST` | `/api/v1/logout` | Bearer JWT | Revoke tokens |

### Products

| Method | Path | Auth required | Description |
|--------|------|---------------|-------------|
| `GET` | `/api/v1/products` | Bearer JWT | List all products (paginated) |
| `GET` | `/api/v1/products/{id}` | Bearer JWT | Get product by ID |
| `POST` | `/api/v1/products` | Bearer JWT | Create a product |
| `PUT` | `/api/v1/products/{id}` | Bearer JWT | Update a product |
| `DELETE` | `/api/v1/products/{id}` | Bearer JWT | Delete a product |

### Manufacturers

| Method | Path | Auth required | Description |
|--------|------|---------------|-------------|
| `GET` | `/api/v1/manufacturers` | Bearer JWT | List all manufacturers |
| `GET` | `/api/v1/manufacturers/products` | Bearer JWT | Manufacturers with their products |

---

## Security

Authentication uses **JWT (HS256)** with separate access and refresh tokens:

| Token | Expiry |
|-------|--------|
| Access token | 24 hours |
| Refresh token | 7 days |

All `/api/v1/products` and `/api/v1/manufacturers` endpoints require a valid `Authorization: Bearer <token>` header.

---

## Architecture

The project follows **Onion Architecture** and is structured as a **Maven multi-module build** — each layer is a separate Maven module with its own `pom.xml`, enforcing compile-time dependency boundaries.

```
pharmacy-back/                          ← root pom.xml  (packaging: pom)
│
├── core/                               ← core/pom.xml  (packaging: pom)
│   ├── domain/          pom.xml        ← domain model: Product, Manufacturer, repository ports, exceptions
│   ├── domain-shared/   pom.xml        ← domain services shared across the core
│   ├── application/     pom.xml        ← use case implementations; depends on domain + application-contracts
│   └── application-contracts/  pom.xml ← service interfaces, DTOs, MapStruct mappers
│
└── external/                           ← external/pom.xml  (packaging: pom)
    ├── app-main/        pom.xml        ← Spring Boot entry point; assembles all modules into one fat JAR
    ├── persistence/     pom.xml        ← Spring Data repository implementations; depends on domain
    ├── security/        pom.xml        ← JWT filter, User/Token JPA entities, Spring Security config
    └── webapi/          pom.xml        ← REST controllers, global exception handler; depends on application-contracts
```

Dependency flow (outer → inner only, never the reverse):

```
webapi / persistence / security
        ↓
   application
        ↓
 application-contracts
        ↓
      domain
```
