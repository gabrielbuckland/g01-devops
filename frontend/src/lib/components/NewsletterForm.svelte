<script lang="ts">
	import { Input } from '$lib/components/ui/input';
	import { Button } from '$lib/components/ui/button';
	import {
		Card,
		CardHeader,
		CardTitle,
		CardDescription,
		CardContent,
		CardFooter
	} from '$lib/components/ui/card';
	import { Validator } from '$lib/validator';
	import { formMessages } from '$lib/constants';
	import { FormService } from '$lib/services/form.service';

	let vorname = '';
	let nachname = '';
	let email = '';
	let status: 'idle' | 'loading' | 'success' | 'error' = 'idle';
	let message = '';

	async function subscribe(e: Event) {
		e.preventDefault();
		status = 'loading';
		message = '';

		try {
			if (Validator.isEmpty(vorname)) {
				throw new Error(formMessages.ERROR_FIRSTNAME_REQUIRED);
			}
			if (Validator.isEmpty(nachname)) {
				throw new Error(formMessages.ERROR_LASTNAME_REQUIRED);
			}
			if (Validator.isEmpty(email)) {
				throw new Error(formMessages.ERROR_EMAIL_REQUIRED);
			}
			if (!Validator.isEmail(email)) {
				throw new Error(formMessages.ERROR_EMAIL_INVALID);
			}

			const response = await FormService.submitForm({
				vorname,
				nachname,
				email
			});

			if (!response.ok) {
				const errorData = await response.json();
				throw new Error(errorData.message || `Ein Fehler ist aufgetreten: ${response.statusText}`);
			}

			status = 'success';
			message = formMessages.SUCCESS(vorname);

			vorname = '';
			nachname = '';
			email = '';
		} catch (err: any) {
			status = 'error';
			message = err.message || formMessages.ERROR_GENERIC;
		}
	}
</script>

<Card class="mx-auto max-w-md bg-white text-gray-900 shadow-lg dark:bg-gray-900 dark:text-gray-100">
	<CardHeader>
		<CardTitle>Newsletter abonnieren</CardTitle>
		<CardDescription class="text-gray-600 dark:text-gray-400">
			Erhalte regelmässig aktuelle Informationen direkt in dein Postfach.
		</CardDescription>
	</CardHeader>

	<form on:submit|preventDefault={subscribe} class="flex flex-col gap-6">
		<CardContent class="flex flex-col gap-3">
			<div class="flex flex-col gap-2 sm:flex-row sm:gap-2">
				<Input
					type="text"
					bind:value={vorname}
					placeholder="Vorname"
					required
					class="w-full sm:w-1/2 dark:border-gray-700 dark:bg-gray-800"
				/>
				<Input
					type="text"
					bind:value={nachname}
					placeholder="Nachname"
					required
					class="w-full sm:w-1/2 dark:border-gray-700 dark:bg-gray-800"
				/>
			</div>

			<Input
				type="email"
				bind:value={email}
				placeholder="E-Mail-Adresse"
				required
				class="w-full dark:border-gray-700 dark:bg-gray-800"
			/>
		</CardContent>

		<CardFooter class="flex flex-col gap-2">
			<Button
				type="submit"
				disabled={status === 'loading'}
				class="flex w-full items-center justify-center gap-2
         bg-indigo-600 text-white hover:bg-indigo-500
         dark:bg-indigo-500 dark:text-white dark:hover:bg-indigo-400"
			>
				{#if status === 'loading'}
					<span class="h-4 w-4 animate-spin rounded-full border-2 border-white border-t-transparent"
					></span>
					Wird gesendet…
				{:else}
					Jetzt abonnieren
				{/if}
			</Button>

			{#if message}
				<p
					data-testid="status-message"
					class={status === 'error' ? 'text-sm text-red-400' : 'text-sm text-green-400'}
				>
					{message}
				</p>
			{/if}
		</CardFooter>
	</form>
</Card>
