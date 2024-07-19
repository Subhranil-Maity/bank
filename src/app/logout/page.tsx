import React from "react";

import { createClient } from '@/utils/supabase/server';
import { revalidatePath } from "next/cache";
export default async function LogOut() {
	revalidatePath('/logout')
	const supabase = createClient()
	const { error } = await supabase.auth.signOut();
	if (error) {
		return (
			<div>
				<h1>{error.message}</h1>
			</div>
		)
	} else {
		return (
			<div>
				<h1>Logged Out</h1>
			</div>
		)
	}
}
