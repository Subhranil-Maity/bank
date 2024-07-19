
import Link from 'next/link';
import React from 'react';
import {
	NavigationMenu,
	NavigationMenuItem,
	NavigationMenuLink,
	NavigationMenuList,
	navigationMenuTriggerStyle,
} from "@/components/ui/navigation-menu"
import { createClient } from '@/utils/supabase/server';
import UserNav from './UserNav';
import UnAuthRightComponent from './UnAuthRightComponent';

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
				UnAuthRightComponent()
				)}
			</div>
		</NavigationMenu>
	);
}

export default Nav;

