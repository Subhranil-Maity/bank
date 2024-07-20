import React from 'react';
import {
	Dialog,
	DialogContent,
	DialogDescription,
	DialogFooter,
	DialogHeader,
	DialogTitle,
	DialogTrigger,
} from "@/components/ui/dialog"
import { Button } from '@/components/ui/button';
import { Label } from '@/components/ui/label';
import SpinningProgress from './SpinningProgress';
import { redirect } from 'next/navigation';

const SignUpDialog = (props: {
	hasSignedUp: boolean,
	submitted: boolean,
	setSubmitted: React.Dispatch<React.SetStateAction<boolean>>,
	content: String,
	error: String
}) => {
	const { hasSignedUp, submitted, setSubmitted, content, error } = props;
	return (
		<Dialog open={submitted} onOpenChange={setSubmitted}>
			<DialogTrigger asChild>
			</DialogTrigger>
			<DialogContent className="sm:max-w-[425px]">
				<DialogHeader>
					<DialogTitle>Signing Up</DialogTitle>
					<DialogDescription>
						Wait While We Sign You Up
					</DialogDescription>
				</DialogHeader>
				{(error.length == 0 ) ? (<div className='py-4 content-center flex flex-row'><SpinningProgress />{content}</div>) : (<p>{error}</p>)}
				<DialogFooter>
					<Button
						variant={hasSignedUp ? "default" : "ghost"}
						disabled={!hasSignedUp}
						onClick={() => {
							setSubmitted(false)
							if (error.length == 0) {
								redirect('/login')
							}
						}}>{(error.length == 0)?("Login"):("Go Back")}</Button>
				</DialogFooter>
			</DialogContent>
		</Dialog>
	);
}

export default SignUpDialog;
// default: "bg-primary text-primary-foreground hover:bg-primary/90",
// destructive:
//   "bg-destructive text-destructive-foreground hover:bg-destructive/90",
// outline:
//   "border border-input bg-background hover:bg-accent hover:text-accent-foreground",
// secondary:
//   "bg-secondary text-secondary-foreground hover:bg-secondary/80",
// ghost: "hover:bg-accent hover:text-accent-foreground",
// link: "text-primary underline-offset-4 hover:underline",
