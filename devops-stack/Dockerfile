# Builds the backend and the frontend of g01-form in one pass.
#
# The build context is the parent directory so that both applications can be
# built from a single Dockerfile, the same way swda-stack does it for its
# service repositories.
#
# g01-form is not modified by this file. In the GitLab pipeline the backend is
# built by Jib directly into the HSLU registry and never had a Dockerfile at
# all, and the frontend keeps its own. Both remain valid and untouched.

FROM maven:3.9.9-eclipse-temurin-21 AS backend-builder
WORKDIR /build
COPY g01-form/backend ./backend
# Jib is declared in the pom but bound to no lifecycle phase, so package
# produces the shaded service jar without ever contacting the HSLU registry.
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -B -q -f backend/pom.xml -DskipTests package

FROM node:22.12.0-alpine AS frontend-builder
WORKDIR /build
COPY g01-form/frontend/package.json g01-form/frontend/package-lock.json ./
RUN npm ci
COPY g01-form/frontend ./
RUN npm run build


FROM amazoncorretto:21.0.4-alpine AS backend
WORKDIR /app
COPY --from=backend-builder /build/backend/target/backend-0.1.jar /app/service.jar
CMD ["sh", "-c", "exec java $JAVA_OPTS -jar /app/service.jar"]

# adapter-node writes its own package.json with type module into build/, and
# the project declares no runtime dependencies, so the build directory alone
# is a complete application. This mirrors g01-form/frontend/Dockerfile.
FROM node:22.12.0-alpine AS frontend
WORKDIR /app
COPY --from=frontend-builder /build/build ./build
CMD ["node", "build/index.js"]
