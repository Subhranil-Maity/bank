'use client';
import React from 'react';
import { useFormState, useFormStatus } from 'react-dom';
import { signup } from '../lib/action';

const initialState = {
	message: '',
}

function SignUpButton(){
	const { pending } = useFormStatus();
	return (
		<button type="submit" aria-disabled={pending} className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
			Sign Up
		</button>
	)
}

const Signup = () => {
	const [state, formAction] = useFormState(signup, initialState);
	return (
		<div className="flex items-center justify-center min-h-screen">
			<div className="border border-red-50  p-8 rounded shadow-md w-full max-w-md">
				<h1 className="text-2xl font-bold mb-6">Login</h1>
				<form className="max-w-sm mx-auto" action={formAction} >
					<div className="mb-5">
						<label htmlFor="email" className="block mb-2 text-sm font-medium ">Your email</label>
						<input type="email" id="email" name='email' className="bg-p-color text-grey-50 text-red-50 focus:outline-none border border-gray-800 text-gray-900 text-sm rounded-lg block w-full p-2.5 " placeholder="email@address.com" required />
					</div>
					<div className="mb-5">
						<label htmlFor="password" className="block mb-2 text-sm font-medium ">Your password</label>
						<input type="password" id="password" name='password' className="bg-b-color border border-gray-800 text-sm rounded-lg focus:outline-none block w-full p-2.5" required />
					</div>
					<SignUpButton />
					<p className='sr-only' role='status'>{state?.message}</p>
				</form>
			</div>
		</div>
	);
}

export default Signup;

