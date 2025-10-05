export const formMessages = {
	// Erfolgsmeldung
	SUCCESS: (name: string) => `Vielen Dank für deine Anmeldung, ${name}!`,

	// Validierungsfehler
	ERROR_FIRSTNAME_REQUIRED: 'Bitte deinen Vornamen eingeben.',
	ERROR_LASTNAME_REQUIRED: 'Bitte deinen Nachnamen eingeben.',
	ERROR_EMAIL_INVALID: 'Bitte eine gültige E-Mail-Adresse eingeben.',
	ERROR_EMAIL_REQUIRED: 'Bitte deine E-Mail-Adresse eingeben.',

	// API- und Netzwerkfehler
	ERROR_NETWORK:
		'Die Verbindung zum Server ist fehlgeschlagen. Bitte prüfe deine Internetverbindung.',
	ERROR_GENERIC_API: 'Ein unerwarteter Fehler auf dem Server ist aufgetreten.',
	ERROR_GENERIC: 'Etwas ist schiefgelaufen. Bitte nochmals versuchen.'
};
