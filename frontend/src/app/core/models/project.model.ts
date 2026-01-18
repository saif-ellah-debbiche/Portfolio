import { Blog } from "./blog.model";

export interface Project{
    id:number;
    title:string;
    images:string[];
    summary:string;
    startDate: Date;
    endDate?: Date;
    duration:string;        
    technologies:string[];
    liveUrl?:string;
    githubUrl?:string;
    blog?:Blog
}