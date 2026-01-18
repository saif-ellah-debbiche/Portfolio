import { Component, inject, signal } from '@angular/core';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { SITE_DATA } from '../../../core/config/siteData';

@Component({
  selector: 'app-education',
  imports: [],
  templateUrl: './education.component.html',
  styleUrl: './education.component.scss'
})
export class EducationComponent {
   private themeService = inject(ThemeService)

  isDarkMode = this.themeService.isDarkMode
  // Sample education data - replace with your actual data
  educationList = SITE_DATA.education;


}
