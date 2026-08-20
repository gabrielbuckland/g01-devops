# g01-form frontend

The newsletter form. SvelteKit on `adapter-node`, Tailwind, form components
from shadcn-svelte under `src/lib/components/ui`.

It posts to the backend at the relative path `/api/form`, which means it only
works behind the Traefik proxy that serves both from one origin. Started on its
own it renders, but every submission fails. See
[devops-stack](../../devops-stack) to run the whole system locally.

## Development

Requires Node 22, the version the image is built from.

```bash
npm ci
npm run dev
```

The dev server listens on port 3000 and proxies `/api` to a backend on
`localhost:8080`, so the same relative path works without a proxy in front.

## Tests

```bash
npm test
```

Vitest runs two projects. `server` covers the plain TypeScript under
`src/lib` in Node. `client` renders the Svelte components in headless Chromium
through Playwright, so a browser has to be installed first:

```bash
npx playwright install --with-deps chromium
```

## Build

```bash
npm run build     # into build/, served by `node build/index.js`
npm run check     # svelte-check against tsconfig.json
```

The `Dockerfile` does the same in two stages and is what CI builds and pushes.
