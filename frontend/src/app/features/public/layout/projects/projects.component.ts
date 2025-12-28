import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ThemeService } from '../../../../core/services/theme/theme.service';
import { Project } from '../../../../core/models/project.model';
import { Blog } from '../../../../core/models/blog.model';
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
  // Sample projects data - replace with your actual data
  projects = signal<Project[]>([
    {
      id: 1,
      title: "Secure Budget Management System with DevOps Integration",
      images: [
        "/assets/images/manager-dashboard.png",
        "/assets/images/admin-dashboard.png",
        "/assets/images/cfo-dashboard.png",
      ],
      date: new Date("2024-01-15"),
      summary:
        "A full-stack e-commerce platform with real-time inventory management, secure payment processing, and advanced analytics dashboard.",
      technologies: ["Angular", "Node.js", "MongoDB", "Stripe", "Docker"],
      blog:this.blog,
      duration:"6 mois",
    },
    {
      id: 2,
      title: "AI Task Manager",
      images: ["/assets/projects/task-manager-1.jpg", "/assets/projects/task-manager-2.jpg"],
      date: new Date("2024-03-20"),
      summary:
        "An intelligent task management system powered by AI that prioritizes tasks, suggests optimal schedules, and provides productivity insights.",
      technologies: ["React", "Python", "TensorFlow", "PostgreSQL", "AWS"],
      blog:this.blog,
      duration:"6 mois",
    },
    {
      id: 3,
      title: "Social Media Dashboard",
      images: [
        "/assets/projects/social-1.jpg",
        "/assets/projects/social-2.jpg",
        "/assets/projects/social-3.jpg",
        "/assets/projects/social-4.jpg",
      ],
      date: new Date("2023-11-10"),
      summary:
        "A comprehensive social media analytics dashboard that aggregates data from multiple platforms and provides actionable insights.",
      technologies: ["Vue.js", "Express", "Redis", "Chart.js", "Firebase"],
      blog:this.blog,
      duration:"6 mois",
    },
  ])



  currentProject = computed(() => this.projects()[this.currentProjectIndex()])
  currentImage = computed(() => {
    const project = this.currentProject()
    return project.images[this.currentImageIndex()]
  })

  totalProjects = computed(() => this.projects().length)
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
  formatDate(date: Date): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "long",
    }).format(date)
  }
}
