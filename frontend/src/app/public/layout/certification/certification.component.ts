import { Component, computed, signal } from '@angular/core';
import { SITE_DATA } from '../../../core/config/siteData';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-certification',
  imports: [CommonModule],
  templateUrl: './certification.component.html',
  styleUrl: './certification.component.scss'
})
export class CertificationComponent {
isDarkMode = computed(() => this.themeService.isDarkMode())

  // Sample certifications data - replace with your actual data
  certifications = SITE_DATA.certifications;

  // Sort certifications by date (latest first)
  sortedCertifications = computed(() => {
    return [...this.certifications].sort((a, b) => b.date.getTime() - a.date.getTime())
  })

  constructor(private themeService: ThemeService) {}

  formatDate(date: Date): string {
    return date.toLocaleDateString("en-US", { year: "numeric", month: "long" })
  }
}
