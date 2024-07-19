'use server'

import { z } from 'zod'

export async function signup(prevState: any, formData: FormData) {
	const schema = z.object({
		email: z.string().email(),
		password: z.string().min(6),
	})
	const data = schema.parse({
		email: formData.get('email'),
		password: formData.get('password'),
	})
	console.log(data)
	return {message: 'success'}

}
