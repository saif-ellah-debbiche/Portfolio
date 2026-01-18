import { Component } from '@angular/core';
import {  RouterOutlet } from '@angular/router';
import { ThemeService } from './core/services/theme/theme.service';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './public/layout/footer/footer.component';
import { NavBarComponent } from './public/layout/nav-bar/nav-bar.component';


@Component({
  selector: 'app-root',
  imports: [CommonModule, RouterOutlet, NavBarComponent, FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'portfolio-frontend';
    constructor(public themeService: ThemeService) {}

}
