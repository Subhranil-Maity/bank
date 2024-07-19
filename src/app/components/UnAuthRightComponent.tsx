import { Button } from '@/components/ui/button';
import Link from 'next/link';
import React from 'react';


export default function UnAuthRightComponent() {
	return (
		<div className="flex items-center gap-x-2 ">
		<Link href="/signup" passHref>
				<Button variant="outline" > Sign Up</Button>
			</Link>
			<Link href="/login" passHref>
				<Button>Login</Button>
			</Link>

		</div>
	)
}

