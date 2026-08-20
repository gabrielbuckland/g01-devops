# g01-form frontend

The newsletter form. SvelteKit on `adapter-node`, Tailwind, form components
from shadcn-svelte under `src/lib/components/ui`.

It posts to the backend at the relative path `/api/form`, which means it only
works behind the Traefik proxy that serves both from one origin. Started on its
own it renders, but every submission fails. See
[devops-stack](../../devops-stack) to run the whole system locally.

## Development

Needs Node `^20.19.0 || >=22.12.0`, which is what Vite requires and what
`engines` in `package.json` declares. Note the gap: Node 21 and 22.0 to 22.11
do not satisfy it. The production image builds on Node 22.12, which is a
separate thing from what the sources need to run.

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

Vitest is configured with two projects. `client` renders the Svelte components
in headless Chromium through Playwright and holds the only tests there are.
`server` is set up for plain TypeScript under `src/lib` in Node but is
currently empty. Running the suite needs a browser, so install one first:

```bash
npx playwright install --with-deps chromium
```

## Build

```bash
npm run build     # into build/, served by `node build/index.js`
npm run check     # svelte-check against tsconfig.json
```

The `Dockerfile` does the same in two stages and is what CI builds and pushes.
