import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { Blog } from '../../../core/models/blog.model';
import { Project } from '../../../core/models/project.model';
import { SITE_DATA } from '../../../core/config/siteData';
@Component({
  selector: 'app-projects',
  imports: [],
  templateUrl: './projects.component.html',
  styleUrl: './projects.component.scss'
})
export class ProjectsComponent implements OnInit {
private themeService = inject(ThemeService)
  isDarkMode = this.themeService.isDarkMode

  // Current project index
  currentProjectIndex = signal(0)

  // Current image index for the active project
  currentImageIndex = signal(0)

  blog:Blog={
    id:2,
    author:"saif",
    content:"",
    date:new Date(),
    createdAt:new Date("2024-01-15"),
    defaultImageLink:"",
    summary:"",
     tags:"",
     title:""

  }

  calculateDuration(startDate: string, endDate?: string): string {
    const start = new Date(startDate);
    const end = endDate ? new Date(endDate) : new Date();
    
    const months = Math.floor((end.getTime() - start.getTime()) / (1000 * 60 * 60 * 24 * 30));
    const years = Math.floor(months / 12);
    const remainingMonths = months % 12;

    if (years > 0 && remainingMonths > 0) {
      return `${years} year${years > 1 ? 's' : ''}, ${remainingMonths} month${remainingMonths > 1 ? 's' : ''}`;
    } else if (years > 0) {
      return `${years} year${years > 1 ? 's' : ''}`;
    } else if (months > 0) {
      return `${months} month${months > 1 ? 's' : ''}`;
    } else {
      return 'Less than a month';
    }
  }
  // Sample projects data - replace with your actual data
  projects :Project[] =SITE_DATA.projects;



  currentProject = computed(() => this.projects[this.currentProjectIndex()])
  currentImage = computed(() => {
    const project = this.currentProject()
    return project.images[this.currentImageIndex()]
  })

  totalProjects = computed(() => this.projects.length)
  totalImages = computed(() => this.currentProject().images.length)

  ngOnInit(): void {
  }

  // Navigate to next image in current project
  nextImage(): void {
    const maxIndex = this.totalImages() - 1
    if (this.currentImageIndex() < maxIndex) {
      this.currentImageIndex.update((i) => i + 1)
    } else {
      this.currentImageIndex.set(0)
    }
  }

  // Navigate to previous image in current project
  previousImage(): void {
    const maxIndex = this.totalImages() - 1
    if (this.currentImageIndex() > 0) {
      this.currentImageIndex.update((i) => i - 1)
    } else {
      this.currentImageIndex.set(maxIndex)
    }
  }

  // Navigate to next project
  nextProject(): void {
    const maxIndex = this.totalProjects() - 1
    if (this.currentProjectIndex() < maxIndex) {
      this.currentProjectIndex.update((i) => i + 1)
    } else {
      this.currentProjectIndex.set(0)
    }
    this.currentImageIndex.set(0) // Reset image index when changing projects
  }

  // Navigate to previous project
  previousProject(): void {
    const maxIndex = this.totalProjects() - 1
    if (this.currentProjectIndex() > 0) {
      this.currentProjectIndex.update((i) => i - 1)
    } else {
      this.currentProjectIndex.set(maxIndex)
    }
    this.currentImageIndex.set(0) // Reset image index when changing projects
  }

  // Go to specific image
  goToImage(index: number): void {
    this.currentImageIndex.set(index)
  }

  // Format date
  formatDate(date: Date|undefined): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "long",
    }).format(date)
  }
}
