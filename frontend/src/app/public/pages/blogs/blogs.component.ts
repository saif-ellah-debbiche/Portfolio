import { Component, computed, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { BlogService } from '../../../core/services/blog/blog.service';
import { Blog } from '../../../core/models/blog.model';


@Component({
  selector: 'app-blogs',
  imports: [RouterLink,NgFor, NgIf],
  templateUrl: './blogs.component.html',
  styleUrl: './blogs.component.scss'
})
export class BlogsComponent implements OnInit {
  blogs: Blog[] = []

  sDarkMode = computed(() => this.themeService.isDarkMode())

  constructor(private themeService: ThemeService,private blogService:BlogService) {}

  ngOnInit(): void {
    this.loadBlogs();
  }

  loadBlogs(): void {
    this.blogService.getBlogs().subscribe({
      next:response=>{
        console.log(response);
        this.blogs=response;
        this.blogs.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
      },
      error:err=>{
        console.log(err);
      }
    })

    // Sort blogs by date (latest first)
  }

  formatDate(date: Date): string {
    return new Date(date).toLocaleDateString("en-US", {
      year: "numeric",
      month: "long",
      day: "numeric",
    })
  }

}
