'use client';
import { createClient } from '@/utils/supabase/client';
import type { NextPage } from 'next';
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';
import { useEffect, useState } from 'react';
import GoToDashboard from '../components/GoToDashboard';
import { time } from 'console';

const Login: NextPage = () => {
	const [shouldRedirect, setShouldRedirect] = useState<boolean>(false)
	const [isLoading, setIsLoading] = useState<boolean>(false)
	const supabase = createClient()
	// useEffect(() => {
	// 	(async () => {
	// 		const user = await supabase.auth.getUser()
	// 		if (user.data.user != null) {
	// 			setShouldRedirect(true)
	// 			// redirect('/dashboard')
	// 		}
	// 	}
	// 	)();
	// }, [supabase, setShouldRedirect])
	supabase.auth.getUser().then((user) => {
		if (user.data.user != null) {
			setShouldRedirect(true)
			// alert ('You are already logged in')
			// redirect('/dashboard')
		}
	}).catch((error) => {
		alert(error)
	})
	if (shouldRedirect) {
		return (
			<div className='h-max size-max content-center self-center'>
				<GoToDashboard />
			</div>
		)
	}
	async function login(formData: FormData) {
		setIsLoading(true);

		// type-casting here for convenience
		// in practice, you should validate your inputs
		const data = {
			email: formData.get('email') as string,
			password: formData.get('password') as string,
		}
		const { error } = await supabase.auth.signInWithPassword(data)

		if (error) {
			// if (error.message == 'email_not_confirmed') {
			// 	redirect('/');
			// 	return
			// }
			alert(error.message)
			setIsLoading(false);
			return
		}
		// revalidatePath('/')
		console.warn('Rebalidate paths by converting the fucn into server aiction')
		redirect('/dashboard')
	}
	// revalidatePath('/login', 'page')

	return (
		<div className="flex items-center justify-center min-h-screen">
			<div className="border border-red-50  p-8 rounded shadow-md w-full max-w-md">
				<h1 className="text-2xl font-bold mb-6">Login</h1>
				<form className="max-w-sm mx-auto"  >
					<div className="mb-5">
						<label htmlFor="email" className="block mb-2 text-sm font-medium ">Your email</label>
						<input type="email" id="email" name='email' className="bg-p-color text-grey-50 text-red-50 focus:outline-none border border-gray-800 text-gray-900 text-sm rounded-lg block w-full p-2.5 " placeholder="email@address.com" required />
					</div>
					<div className="mb-5">
						<label htmlFor="password" className="block mb-2 text-sm font-medium ">Your password</label>
						<input type="password" id="password" name='password' className="bg-b-color border border-gray-800 text-sm rounded-lg focus:outline-none block w-full p-2.5" required />
					</div>
					<button type="submit" disabled={isLoading} formAction={login} className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
						{isLoading ? 'Loading...' : 'Submit'}
					</button>
				</form>
			</div>
		</div>
	);
};

export default Login;

