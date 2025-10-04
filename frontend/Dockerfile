# ---- Build Stage ----
FROM node:22-alpine AS build

ARG PUBLIC_BACKEND_API_URL

ENV PUBLIC_BACKEND_API_URL=$PUBLIC_BACKEND_API_URL

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run build

# ---- Run Stage ----
FROM node:22-alpine

WORKDIR /app

COPY --from=build /app/build ./build
COPY --from=build /app/package.json ./package.json

RUN npm install --omit=dev

EXPOSE 3000

# Starte die Node.js-Anwendung
CMD ["node", "build/index.js"]