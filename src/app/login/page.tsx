'use client';
import { createClient } from '@/utils/supabase/client';
import { redirect } from 'next/navigation';
import { useState } from 'react';
import GoToDashboard from '../components/GoToDashboard';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import Link from 'next/link';

const formSchema = z.object({
	email: z.string().email(),
	password: z.string().min(3),
})

const Login = () => {
	const form = useForm<z.infer<typeof formSchema>>({
		resolver: zodResolver(formSchema),
		defaultValues: {
			email: '',
			password: '',
		}
	})
	const [shouldRedirect, setShouldRedirect] = useState<boolean>(false)
	const supabase = createClient()
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
	const handleSubmit = async (value: z.infer<typeof formSchema>) => {
		const data = {
			email: value.email,
			password: value.password,
		}
		const { error } = await supabase.auth.signInWithPassword(data)

		if (error) {

			alert(error.message)
			return
		}
		console.warn('Rebalidate paths by converting the fucn into server aiction')
		redirect('/dashboard')
	}

	return (
		<div className='content-center self-center justify-cente'>
			<Card className='w-[350px] ml-[35%] mt-[100px]'>
				<Form {...form}>
					<form onSubmit={form.handleSubmit(handleSubmit)}>
						<CardHeader><CardTitle>Login</CardTitle></CardHeader>
						<CardContent>
							<FormField control={form.control} name='email' render={({ field }) => {
								return <FormItem>
									<FormLabel>Email</FormLabel>
									<FormControl >
										<Input {...field} />
									</FormControl>
									<FormMessage />
								</FormItem>
							}} />
							<FormField control={form.control} name='password' render={({ field }) => {
								return <FormItem>
									<FormLabel>Password</FormLabel>
									<FormControl >
										<Input {...field} />
									</FormControl>
									<FormMessage />
								</FormItem>
							}} />

						</CardContent>
						<CardFooter className='flex justify-between'>
							<Link href="/signup">
								<Button variant="outline">Sign Up</Button>
							</Link>
							<Button type='submit'>Login</Button>
						</CardFooter>
					</form>
				</Form>

			</Card>
		</div>
	);
};

export default Login;

