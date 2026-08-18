import { render, fireEvent, cleanup, screen } from '@testing-library/svelte';
import { describe, it, expect, afterEach, beforeEach } from 'vitest';
import NewsletterForm from './NewsletterForm.svelte';
import { formMessages } from '$lib/constants';

// Stellt sicher, dass der DOM nach jedem Test sauber ist
afterEach(() => cleanup());

// Stellt sicher, dass die Mocks vor jedem Test zurückgesetzt werden
beforeEach(() => {
	fetchMock.resetMocks();
});

describe('NewsletterForm Component Test', () => {
	it('sollte das Formular korrekt rendern', () => {
		expect.hasAssertions();
		render(NewsletterForm);
		expect(screen.getByText('Newsletter abonnieren')).toBeInTheDocument();
		expect(screen.getByRole('button', { name: /Jetzt abonnieren/i })).toBeInTheDocument();
	});

	it('sollte bei einer erfolgreichen API-Antwort eine Erfolgsmeldung anzeigen', async () => {
		expect.hasAssertions();
		fetchMock.mockResponseOnce(JSON.stringify({ message: 'Erfolgreich gespeichert' }), {
			status: 201
		});

		render(NewsletterForm);

		await fireEvent.input(screen.getByPlaceholderText('Vorname'), { target: { value: 'Maria' } });
		await fireEvent.input(screen.getByPlaceholderText('Nachname'), { target: { value: 'Muster' } });
		await fireEvent.input(screen.getByPlaceholderText('E-Mail-Adresse'), {
			target: { value: 'maria@muster.com' }
		});
		await fireEvent.click(screen.getByRole('button', { name: /Jetzt abonnieren/i }));

		const successMessage = await screen.findByTestId('status-message');
		expect(successMessage).toBeInTheDocument();
		expect(successMessage).toHaveTextContent(formMessages.SUCCESS('Maria'));
	});

	it('sollte bei einer Fehler-API-Antwort eine Fehlermeldung anzeigen', async () => {
		expect.hasAssertions();
		fetchMock.mockResponseOnce(JSON.stringify({ message: 'Diese E-Mail existiert bereits!' }), {
			status: 409
		});

		render(NewsletterForm);

		await fireEvent.input(screen.getByPlaceholderText('Vorname'), { target: { value: 'John' } });
		await fireEvent.input(screen.getByPlaceholderText('Nachname'), { target: { value: 'Doe' } });
		await fireEvent.input(screen.getByPlaceholderText('E-Mail-Adresse'), {
			target: { value: 'john@doe.com' }
		});
		await fireEvent.click(screen.getByRole('button', { name: /Jetzt abonnieren/i }));

		const errorMessage = await screen.findByTestId('status-message');
		expect(errorMessage).toBeInTheDocument();
		expect(errorMessage).toHaveTextContent('Diese E-Mail existiert bereits!');
	});
});
