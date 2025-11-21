# g01-form

A [Micronaut](https://micronaut.io/) Java application built for demonstrating DEVOPS practices.

---

## Requirements

- [Java 21](https://adoptium.net/)
- [Maven 3.8+](https://maven.apache.org/)
- [Docker](https://www.docker.com/)

---

## Development

1. Make changes in the code.

2. Start the Postgres database locally:

```bash
docker compose -f docker_local.yml up -d
```

3. Build and run the application locally:

```bash
mvn package
MICRONAUT_ENVIRONMENTS=local java -jar ./target/g01-form-0.1.jar
# or use your IDE build to start the Java application

```

4. The application will be available at: [http://localhost:8080](http://localhost:8080)

### Database migrations
- Flyway runs automatically on startup; place new migration scripts in `backend/src/main/resources/db/migration` using the `V##__description.sql` naming convention.

---

## Starting everything inside docker just like on the VMs
This setup also boots up traefik that manages the traffic to the frontend and backend and simplifies the networking
### Test

1. Copy the environment template and configure secrets:

```bash
cp .env.example .env
```

1. Start the application and database using the test compose file:

```bash
docker compose -f docker_test.yml up -d
# docker compose should automatically inject variables from the .env file
# if not, add the `--env-file .env` option
```

### Production

1. Copy the environment template and configure secrets:

```bash
cp .env.example .env
```

1. Start the application and database using the production compose file:

```bash
docker compose -f docker_prod.yml up -d
# docker compose should automatically inject variables from the .env file
# if not, add the `--env-file .env` option
```

1. Access the application under [http://localhost](http://localhost). Or the corresponding server IP/domain.


---

## Running Tests

```bash
mvn test
```

## Running the frontend locally

### Requirements

- Node.js (>= 20.19.0)
- npm (>= 10)

### Steps

1. Wechsel in das Frontend-Verzeichnis:
   ```bash
   cd frontend

   ```
2. Install dependencies

   ```bash
   npm install

   ```

3. Start Server
   ```bash
   npm run dev -- --open
   ```
