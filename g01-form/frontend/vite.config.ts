// vite.config.ts

import tailwindcss from '@tailwindcss/vite';
import { defineConfig } from 'vitest/config';
import { sveltekit } from '@sveltejs/kit/vite';

export default defineConfig({
	plugins: [tailwindcss(), sveltekit()],
	server: {
		port: 3000,
		proxy: {
			'/api': {
				target: 'http://localhost:8080',
				changeOrigin: true,
				secure: false
			}
		}
	},
	test: {
		expect: { requireAssertions: true },
		// Der Rest Ihrer Test-Konfiguration bleibt unverändert
		projects: [
			{
				extends: './vite.config.ts',
				test: {
					name: 'client',
					globals: true, // <-- DIESE ZEILE HINZUFÜGEN
					environment: 'browser',
					browser: {
						enabled: true,
						provider: 'playwright',
						headless: true, // headless ist gut für CI
						instances: [{ browser: 'chromium' }]
					},
					include: ['src/**/*.svelte.{test,spec}.{js,ts}'],
					exclude: ['src/lib/server/**'],
					setupFiles: ['./vitest-setup-client.ts']
				}
			},
			{
				extends: './vite.config.ts',
				test: {
					name: 'server',
					environment: 'node',
					include: ['src/**/*.{test,spec}.{js,ts}'],
					exclude: ['src/**/*.svelte.{test,spec}.{js,ts}']
				}
			}
		]
	}
});
