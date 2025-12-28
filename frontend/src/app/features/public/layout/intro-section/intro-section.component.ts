import { AfterViewInit, Component, computed, ElementRef, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ThemeService } from '../../../../core/services/theme/theme.service';
import { PortfolioOwner } from '../../../../core/models/user.model';

@Component({
  selector: 'app-intro-section',
  imports: [CommonModule],
  templateUrl: './intro-section.component.html',
  styleUrl: './intro-section.component.scss'
})
export class IntroSectionComponent implements AfterViewInit, OnDestroy {
 private themeService = inject(ThemeService)
  isDarkMode = this.themeService.isDarkMode

  displayedName = signal("")
  private typingInterval: any
  private isDeleting = signal(false)
  private charIndex = signal(0)
  private observer!: IntersectionObserver;

  constructor(private el: ElementRef) {}


    ngAfterViewInit() {
    this.observer = new IntersectionObserver(([entry]) => {
      if (entry.isIntersecting) {
        this.startTypewriterEffect();
      } else {
        this.stopTypewriterEffect();
      }
    }, { threshold: 0.1 }); // 10% visible triggers

    this.observer.observe(this.el.nativeElement);
  }

stopTypewriterEffect() {
    clearInterval(this.typingInterval);
    this.typingInterval = null;
  }

  // Mock data - replace with actual service call
  portfolioOwner = signal<PortfolioOwner>({
    id:1,
    firstName: "Saif Ellah",
    lastName: "Debbiche",
    image: "/assets/images/ownerImage.png",
    bio: "Aspiring Cloud Engineer | Computer Science Graduate | Engineering Student.",
    email: "saifellah.debbiche@esprit.tn",
    contacts: [
      {
        name: "GitHub",
        darkLogo: "/assets/icons/github-dark.png",
        lightLogo: "/assets/icons/github-light.png",
        redirectLink: "https://github.com/johndoe",
      },
      {
        name: "LinkedIn",
        darkLogo: "/assets/icons/linkedin-dark.png",
        lightLogo: "/assets/icons/linkedin-light.png",
        redirectLink: "https://linkedin.com/in/johndoe",
      },
    ],
  })

  fullName = computed(() => {
    const owner = this.portfolioOwner()
    return `${owner.firstName} ${owner.lastName}`
  })

  ngOnInit() {
  }

  ngOnDestroy() {
    if (this.typingInterval) {
      clearInterval(this.typingInterval)
    }
  }

  private startTypewriterEffect() {
    const typingSpeed = 150
    const deletingSpeed = 100
    const pauseTime = 2000

    this.typingInterval = window.setInterval(
      () => {
        const fullName = this.fullName()
        const currentIndex = this.charIndex()
        const deleting = this.isDeleting()

        console.log("STILL WORKING")
        if (!deleting && currentIndex < fullName.length) {
          // Typing
          this.displayedName.set(fullName.substring(0, currentIndex + 1))
          this.charIndex.set(currentIndex + 1)
        } else if (!deleting && currentIndex === fullName.length) {
          // Pause before deleting
          setTimeout(() => this.isDeleting.set(true), pauseTime)
        } else if (deleting && currentIndex > 0) {
          // Deleting
          this.displayedName.set(fullName.substring(0, currentIndex - 1))
          this.charIndex.set(currentIndex - 1)
        } else if (deleting && currentIndex === 0) {
          // Start typing again
          this.isDeleting.set(false)
        }
      },
      this.isDeleting() ? deletingSpeed : typingSpeed,
    )
  }

  getContactLogo(contact: any): string {
    return this.isDarkMode() ? contact.lightLogo : contact.darkLogo
  }
}
