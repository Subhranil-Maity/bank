
import Link from 'next/link';
import React from 'react';
import NavRightComponent from './NavRightComponent';
import {
	NavigationMenu,
	NavigationMenuContent,
	NavigationMenuIndicator,
	NavigationMenuItem,
	NavigationMenuLink,
	NavigationMenuList,
	NavigationMenuTrigger,
	navigationMenuTriggerStyle,
	NavigationMenuViewport,
} from "@/components/ui/navigation-menu"
import { Button } from '@/components/ui/button';
import { createClient } from '@/utils/supabase/server';
import UserNav from './UserNav';

const items: { title: string; href: string }[] = [
	{
		title: 'Home',
		href: '/',
	},
	{
		title: 'Dashboard',
		href: '/dashboard',
	},
];

const Nav = async () => {
	const user = (await createClient().auth.getUser()).data.user ;
	console.log(user);
	return (
		<NavigationMenu className='p-2 w-full max-w-[100%]'>
					<div className='w-ful'>
			<NavigationMenuList className=''>
				{items.map((item) => (
					<NavigationMenuItem key={item.href}>
						<Link href={item.href} legacyBehavior passHref>
							<NavigationMenuLink className={navigationMenuTriggerStyle()}>
								{item.title}
							</NavigationMenuLink>
						</Link>
					</NavigationMenuItem>
				))}

			</NavigationMenuList>
					</div>
			<div className="flex items-center gap-x-2 ms-auto md:col-span-3">
				{user ? (
					<UserNav />
				) : (
					<div className="flex items-center gap-x-2">
						ello
					</div>
				)}
			</div>
		</NavigationMenu>


		// <nav className=" p-2">
		// <div className='container mx-auto flex justify-between items-center'>
		// 	<ul className="flex space-x-4">
		// 		<li>
		// 			<Link href="/" className='text-white hover:text-gray-300'>
		// 				Home
		// 			</Link>
		// 		</li>
		// 		<li>
		// 			<Link href="/dashboard" className='text-white hover:text-gray-300'>
		// 				Dashboard
		// 			</Link>
		// 		</li>
		// 	</ul>
		// 	<NavRightComponent />
		// 	</div>
		// </nav>
	);
}

export default Nav;

