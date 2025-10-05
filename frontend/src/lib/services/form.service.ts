import { PUBLIC_BACKEND_API_URL } from '$env/static/public';
import type { FormModel } from '$lib/model/form.model';

export class FormService {
	static readonly API_URL = PUBLIC_BACKEND_API_URL + '/form';

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
