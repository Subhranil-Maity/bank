'use client';
import React, { useState } from 'react';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { ResisterUserZSchema } from '@/schema/ResisterUser';
import { zodResolver } from '@hookform/resolvers/zod';
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from '@/components/ui/card';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import Link from 'next/link';
import { createClient } from '@/utils/supabase/client';
import SignUpDialog from '../components/SignUpDialog';
import ConfirmEmailDialog from '../components/ConfirmEmailDialog';


const Signup = () => {
	const [submited, setSubmited] = useState(false)
	const [hasSignedUp, setHasSignedUp] = useState(false)
	const [content, setContent] = useState('')
	const [error, setError] = useState('')
	const [openSuccessDialog, setOpenSuccessDialog] = useState(false)
	const form = useForm<z.infer<typeof ResisterUserZSchema>>({
		resolver: zodResolver(ResisterUserZSchema),
		defaultValues: {
			email: '',
			password: '',
			confirmpassword: '',
			address: '',
			phone: '',
			firstname: '',
			lastname: '',
		}
	})

	const handleSubmit = async (value: z.infer<typeof ResisterUserZSchema>) => {
		setSubmited(true)
		const data = {
			email: value.email,
			password: value.password,
			address: value.address,
			phone: value.phone,
			firstname: value.firstname,
			lastname: value.lastname,
		}
		setContent('Connecting To Server')
		const supabase = createClient()
		setContent('Creating Your Account')
		const { error } = await supabase.auth.signUp({
			email: value.email,
			password: value.password,
		})
		if (error) {
			// alert(error.message)
			setError(error.message)
			return
		}else{
			setHasSignedUp(true)
			setContent('Account Created')
			setOpenSuccessDialog(true)
		}
		setSubmited(false)
		setError('')
	}
	return (
		<div className='content-center self-center justify-cente'>
		<SignUpDialog hasSignedUp={hasSignedUp} submitted={submited} setSubmitted={setSubmited} content={content} error={error}/>
		<ConfirmEmailDialog open={openSuccessDialog} setOpen={setOpenSuccessDialog}/>
			<Card className='w-[350px] ml-[35%] mt-[0.25rem]'>
				<Form {...form}>
					<form onSubmit={form.handleSubmit(handleSubmit)}>
						<CardHeader>
							<CardTitle>Sign Up</CardTitle>
							<CardDescription>Become A Member</CardDescription>
						</CardHeader>
						<CardContent>
							<div className='flex flex-row'>
								<FormField control={form.control} name='firstname' render={({ field }) => {
									return <FormItem className='pr-2'>
										<FormLabel>First Name</FormLabel>
										<FormControl >
											<Input {...field} />
										</FormControl>
										<FormMessage />
									</FormItem>
								}} />
								<FormField control={form.control} name='lastname' render={({ field }) => {
									return <FormItem>
										<FormLabel>Last Name</FormLabel>
										<FormControl >
											<Input {...field} />
										</FormControl>
										<FormMessage />
									</FormItem>
								}} />
							</div>
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
										<Input {...field} type='password' />
									</FormControl>
									<FormMessage />
								</FormItem>
							}} />
							<FormField control={form.control} name='confirmpassword' render={({ field }) => {
								return <FormItem>
									<FormLabel>Confirm Password</FormLabel>
									<FormControl >
										<Input {...field} type='password' />
									</FormControl>
									<FormMessage />
								</FormItem>
							}} />
							<FormField control={form.control} name='address' render={({ field }) => {
								return <FormItem>
									<FormLabel>Address</FormLabel>
									<FormControl >
										<Input {...field} />
									</FormControl>
									<FormMessage />
								</FormItem>
							}} />
							<FormField control={form.control} name='phone' render={({ field }) => {
								return <FormItem>
									<FormLabel>Phone</FormLabel>
									<FormControl >
										<Input {...field} />
									</FormControl>
									<FormMessage />
								</FormItem>
							}} />
						</CardContent>
						<CardFooter className='flex justify-between'>
							<Link href="/login">
								<Button variant="outline">Sign In</Button>
							</Link>
							<Button type='submit' >Sign Up</Button>
						</CardFooter>
					</form>
				</Form>
			</Card>
		</div>
	);
}

export default Signup;

