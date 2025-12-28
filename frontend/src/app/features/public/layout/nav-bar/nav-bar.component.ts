import { Component, signal } from '@angular/core';
import { ThemeService } from '../../../../core/services/theme/theme.service';

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {
isMenuOpen = signal(false)

  navItems = [
    { label: "Home", href: "#home" },
    { label: "Projects", href: "#projects" },
    { label: "Education", href: "#education" },
    { label: "Certificates", href: "#certificates" },
    { label: "Blogs", href: "blogs" },
  ]

  constructor(public themeService: ThemeService) {}

  toggleMenu(): void {
    this.isMenuOpen.update((value) => !value)
  }

  toggleTheme(): void {
    this.themeService.toggleTheme()
  }

  closeMenu(): void {
    this.isMenuOpen.set(false)
  }
}
