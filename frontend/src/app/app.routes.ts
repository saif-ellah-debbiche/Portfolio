import { Routes } from '@angular/router';
import { HomeComponent } from './features/public/pages/home/home.component';
import { BlogComponent } from './features/public/pages/blog/blog.component';
import { BlogsComponent } from './features/public/pages/blogs/blogs.component';
import { LoginComponent } from './features/auth/pages/login/login.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'blogs', component: BlogsComponent },
    { path: 'blogs/:blogTitle', component: BlogComponent },
    { path: 'login', component: LoginComponent},
  { path: '**', redirectTo: '' } // wildcard redirect
];
