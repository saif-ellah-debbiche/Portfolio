import { Component, inject, signal } from '@angular/core';
import { ThemeService } from '../../../../core/services/theme/theme.service';
import { Education } from '../../../../core/models/education.model';

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
  educationList = signal<Education[]>([
    {
      universityName: "Stanford University",
      logo: "/assets/universities/stanford.png",
      degree: "Master of Science in Computer Science",
      status: "completed",
      startDate: "2020",
      endDate: "2022",
      description: "Specialized in Artificial Intelligence and Machine Learning",
    },
    {
      universityName: "University of California, Berkeley",
      logo: "/assets/universities/berkeley.png",
      degree: "Bachelor of Science in Software Engineering",
      status: "completed",
      startDate: "2016",
      endDate: "2020",
      description: "Graduated with honors, GPA: 3.8/4.0",
    },
    {
      universityName: "MIT",
      logo: "/assets/universities/mit.png",
      degree: "PhD in Computer Science",
      status: "in-progress",
      startDate: "2023",
      endDate: "Present",
      description: "Research focus on Distributed Systems and Cloud Computing",
    },
  ])


}
