'use client';
import Link from 'next/link';
import React from 'react';

const GoToDashboard = () => {
	// console.log(user);
	return (
		<div className='content-center self-center size-full h-max'>
			<Link href="/dashboard" className="bg-blue-500 text-white px-2 py-2 rounded hover:bg-blue-700 self-center content-center">
				Go To Dasboard
			</Link>
		</div>
	);
}

export default GoToDashboard;

