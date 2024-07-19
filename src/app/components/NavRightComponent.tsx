import { createClient } from '@/utils/supabase/server';
import Link from 'next/link';
import React from 'react';

const NavRightComponent = async () => {
	const supabase = createClient()
	const user = await supabase.auth.getUser();
	// console.log(user);
	const isLogged = (user.data.user == null) ? false : true;
	return (
		<div>
			<Link href={isLogged ? '/logout' : '/login'} className="bg-blue-500 text-white px-2 py-2 rounded hover:bg-blue-700">
				{isLogged ? 'Logout' : 'Login'}
			</Link>
		</div>
	);
}

export default NavRightComponent;

