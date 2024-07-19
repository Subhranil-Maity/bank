import { createClient } from '@/utils/supabase/client';
import { warn } from 'console';
import { revalidatePath } from 'next/cache';
import { redirect } from 'next/navigation';
import React from 'react';

export default async function Dashboard(){
	revalidatePath('/dashboard');
	const supabase = createClient();
	return (
		<div>
			<h1>Dashboard</h1>
		</div>
	);
};

