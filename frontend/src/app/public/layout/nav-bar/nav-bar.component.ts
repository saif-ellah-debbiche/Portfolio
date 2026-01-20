import { Component, signal } from '@angular/core';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { SITE_DATA } from '../../../core/config/siteData';

@Component({
  selector: 'app-nav-bar',
  imports: [],
  templateUrl: './nav-bar.component.html',
  styleUrl: './nav-bar.component.scss'
})
export class NavBarComponent {
isMenuOpen = signal(false)
certifications=SITE_DATA.certifications;
  navItems = [
    { label: "Home", href: "#home" },
    { label: "Projects", href: "#projects" },
    { label: "Education", href: "#education" },
    { label: "Blogs", href: "blogs" },
  ]
  resumePath = 'assets/Saif_Ellah_Debbiche_resume.pdf';
  constructor(public themeService: ThemeService) {
    if(this.certifications.length>0){
      this.navItems.splice(3, 0,{ label: "Certificates", href: "#certificates" });
    }
  }

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
