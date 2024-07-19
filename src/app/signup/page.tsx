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
		<div className='h-max'>
		</div>
	);
}

export default Signup;

