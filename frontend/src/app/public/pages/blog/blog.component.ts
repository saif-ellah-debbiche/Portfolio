import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { MarkdownModule } from 'ngx-markdown';
import { BlogService } from '../../../core/services/blog/blog.service';
import { Blog } from '../../../core/models/blog.model';

@Component({
  selector: 'app-blog',
  imports: [MarkdownModule],
  templateUrl: './blog.component.html',
  styleUrl: './blog.component.scss'
})
export class BlogComponent implements OnInit{
  blog:Blog|null=null;  
  
  constructor(private route: ActivatedRoute,private blogService:BlogService) {}

ngOnInit(): void {
      // Asynchronous access (observable)
      this.route.paramMap.subscribe((params: ParamMap) => {
        const title = params.get('blogTitle');
        title && this.blogService.getBlog(title).subscribe({
          next:blog=>{
            console.log(blog)
            this.blog=blog
          }
        })
        
      });
    }
}
