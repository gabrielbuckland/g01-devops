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
java -jar ./target/g01-form-0.1.jar
# or use your IDE build to start the Java application

```

4. The application will be available at: [http://localhost:8080](http://localhost:8080)

---

## Production

1. Copy the environment template and configure secrets:

```bash
cp .env.example .env
```

2. Start the application and database using the production compose file:

```bash
docker compose -f docker_prod.yml up -d
# docker compose should automatically inject variables from the .env file
# if not, add the `--env-file .env` option
```

3. Access the application under [http://localhost:8080](http://localhost:8080). Or the corresponding server IP/domain.

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
   cd src/main/java/ch/hslu/devops/g01/frontend

   ```
2. Install dependencies

   ```bash
   npm install

   ```

3. Start Server
   ```bash
   npm run dev -- --open
   ```
