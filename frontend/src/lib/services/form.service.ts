import type { FormModel } from '$lib/model/form.model';

export class FormService {
	static readonly API_URL =   '/api/form';

	static async submitForm(data: FormModel): Promise<Response> {
		return await fetch(FormService.API_URL, {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify({
				vorname: data.vorname,
				nachname: data.nachname,
				email: data.email
			})
		});
	}
}
