export class Validator {
	static isEmail(email: string): boolean {
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		return emailRegex.test(email.toLowerCase());
	}

	static isEmpty(value: string | null | undefined): boolean {
		return !value || value.trim().length === 0;
	}

	static isNotEmpty(value: string | null | undefined): boolean {
		return !this.isEmpty(value);
	}
}
