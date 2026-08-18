# ---- Build Stage ----
FROM node:22-alpine AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

COPY . .

RUN npm run build

# ---- Run Stage ----
FROM node:22-alpine

WORKDIR /app

COPY --from=build /app/build ./build

EXPOSE 80

# Starte die Node.js-Anwendung
CMD ["node", "build/index.js"]