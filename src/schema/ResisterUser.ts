import { z } from "zod";


export const ResisterUserZSchema = z.object({
	email: z.string().email({ "message": "Invalid email" }),
	password: z.string().min(3),
	confirmpassword: z.string().min(3),
	address: z.string().min(3).max(255),
	phone: z.string().length(10),
	firstname: z.string().min(3),
	lastname: z.string().min(3),
}).refine(data => data.password === data.confirmpassword, { message: "Passwords do not match" }).refine(data => data.phone.match(/^[0-9]+$/) !== null, { message: "Invalid phone number" });



