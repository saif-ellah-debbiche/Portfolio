import { Contact } from "./contact.model";

export interface PortfolioOwner{
    id:number;
    image:string;
    bio:string;
    firstName: string;
    /** The user's last name */
    lastName: string;
    /** The user's email address, often unique */
    email: string;
    /** An optional property for the user's phone number */
    phone?: string;
    contacts:Contact[]

}