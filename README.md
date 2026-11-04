# spring-boot-nextjs-sample1

[![Java CI with Gradle](https://github.com/hendisantika/spring-boot-nextjs-sample1/actions/workflows/gradle.yml/badge.svg)](https://github.com/hendisantika/spring-boot-nextjs-sample1/actions/workflows/gradle.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?logo=springboot)
![Next.js](https://img.shields.io/badge/Next.js-15-black?logo=nextdotjs)

Sample full-stack app: a **Spring Boot** REST API (JWT auth, articles & members) with a **Next.js** frontend.

## Tech Stack

| Layer    | Technology                                                                 |
|----------|----------------------------------------------------------------------------|
| Backend  | Java 25, Spring Boot 4.1, Spring Security, Spring Data JPA, Validation      |
| Auth     | JWT (JJWT 0.13), stateless sessions                                         |
| API docs | springdoc-openapi (Swagger UI)                                              |
| Database | H2 (default), MySQL / MariaDB via Docker Compose                            |
| Build    | Gradle (Kotlin DSL) with wrapper, Java toolchain 25                         |
| Frontend | Next.js 15, React 19, TanStack Query, Axios, Tailwind CSS                   |
| CI       | GitHub Actions (build + test on Temurin JDK 25, dependency graph submission)|

## Prerequisites

- JDK 25 (the Gradle toolchain targets Java 25)
- Node.js 20+ and npm (for the frontend)
- Docker (optional, for MySQL/MariaDB)

## Project Structure

```
.
├── src/main/java/id/my/hendisantika/backend
│   ├── domain/article     # Article entity, service, REST controller
│   ├── domain/member      # Member entity, service, login/logout/me
│   └── global/RsData      # Response wrapper, JWT, security & web config
├── frontend/              # Next.js app (calls http://localhost:8080/api/v1/)
├── compose.yml            # MySQL 9.1
└── mariadb-compose.yaml   # MariaDB 10.7
```

## Running the Backend

```bash
./gradlew bootRun
```

The API runs at `http://localhost:8080`, with Swagger UI at `http://localhost:8080/swagger-ui/index.html`.

Copy `src/main/resources/application-secret.yml.default` to `application-secret.yml` and fill in your own secrets (e.g. `custom.jwt.secretKey`).

To use MySQL instead of H2:

```bash
docker compose -f compose.yml up -d
```

### Build & Test

```bash
./gradlew build
```

## Running the Frontend

```bash
cd frontend
npm install
npm run dev
```

Open `http://localhost:3000`.

## API Endpoints

Login sets `accessToken` / `refreshToken` cookies (JWT) that are read by `JwtAuthorizationFilter`.

| Method | Path                       | Description            |
|--------|----------------------------|------------------------|
| POST   | `/api/v1/members/login`    | Log in, issue JWT      |
| POST   | `/api/v1/members/logout`   | Log out                |
| GET    | `/api/v1/members/me`       | Current member         |
| GET    | `/api/v1/articles`         | List articles          |
| GET    | `/api/v1/articles/{id}`    | Article detail         |
| POST   | `/api/v1/articles`         | Create article         |
| PATCH  | `/api/v1/articles/{id}`    | Update article         |
| DELETE | `/api/v1/articles/{id}`    | Delete article         |

## CI/CD & Dependency Updates

- **GitHub Actions** (`.github/workflows/gradle.yml`): builds and tests on Temurin JDK 25 for pushes and PRs to `main`, and submits the Gradle dependency graph for Dependabot alerts.
- **Dependabot** (`.github/dependabot.yml`):
  - Gradle: daily, grouped (Spring Boot, JJWT, springdoc, JDBC drivers, minor/patch)
  - npm (`/frontend`): weekly, grouped (Next.js/React, TanStack, dev tooling)
  - GitHub Actions: weekly, grouped into a single PR
  - Labeled PRs with conventional commit prefixes (`build(deps)`, `ci(deps)`)

## Author

**Hendi Santika**
- Email: hendisantika@gmail.com
- Telegram: [@hendisantika34](https://t.me/hendisantika34)
- Link: [s.id/hendisantika](https://s.id/hendisantika)
