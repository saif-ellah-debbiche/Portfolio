import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subscribable } from 'rxjs';
import { Blog } from '../../models/blog.model';

@Injectable({
  providedIn: 'root'
})
export class BlogService {
  base_url= "http://localhost:8081/clients";
  constructor(private http:HttpClient) {

   }

   getBlogs():Observable<Blog[]>{
    return this.http.get<Blog[]>(this.base_url+"/blogs");
   }
   getBlog(title:string):Observable<Blog>{
    return this.http.get<Blog>(this.base_url+"/blogs/"+title);
   }

}
