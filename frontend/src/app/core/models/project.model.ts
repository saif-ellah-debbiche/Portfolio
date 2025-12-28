import { Blog } from "./blog.model";

export interface Project{
    id:number;
    title:string;
    images:string[];
    summary:string;
    date:Date;
    technologies:string[];
    duration:string;
    blog:Blog
}