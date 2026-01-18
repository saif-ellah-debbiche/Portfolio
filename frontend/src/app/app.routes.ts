import { Routes } from '@angular/router';
import { HomeComponent } from './public/pages/home/home.component';
import { BlogsComponent } from './public/pages/blogs/blogs.component';
import { BlogComponent } from './public/pages/blog/blog.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'blogs', component: BlogsComponent },
    { path: 'blogs/:blogTitle', component: BlogComponent },
    { path: '**', redirectTo: '' } // wildcard redirect
];
