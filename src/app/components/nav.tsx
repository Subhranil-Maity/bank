
import Link from 'next/link';
import React from 'react';
import NavRightComponent from './NavRightComponent';

const Nav = async () => {
	return (
		<nav className=" p-2">
		<div className='container mx-auto flex justify-between items-center'>
			<ul className="flex space-x-4">
				<li>
					<Link href="/" className='text-white hover:text-gray-300'>
						Home
					</Link>
				</li>
				<li>
					<Link href="/dashboard" className='text-white hover:text-gray-300'>
						Dashboard
					</Link>
				</li>
			</ul>
			<NavRightComponent />
			</div>
		</nav>
	);
}

export default Nav;

