import { Component, computed, signal } from '@angular/core';
import { Certification } from '../../../../core/models/certificate.model';
import { ThemeService } from '../../../../core/services/theme/theme.service';

@Component({
  selector: 'app-certification',
  imports: [],
  templateUrl: './certification.component.html',
  styleUrl: './certification.component.scss'
})
export class CertificationComponent {
isDarkMode = computed(() => this.themeService.isDarkMode())

  // Sample certifications data - replace with your actual data
  certifications = signal<Certification[]>([
    {
      providerName: "Amazon Web Services",
      providerLogo: "/assets/logos/aws.svg",
      title: "AWS Certified Solutions Architect - Professional",
      date: new Date("2024-03-15"),
      summary:
        "Advanced certification demonstrating expertise in designing distributed systems and applications on AWS platform with focus on scalability, security, and cost optimization.",
      status: "completed",
      credentialUrl: "https://aws.amazon.com/verification",
    },
    {
      providerName: "Google Cloud",
      providerLogo: "/assets/logos/gcp.svg",
      title: "Google Cloud Professional Cloud Architect",
      date: new Date("2023-11-20"),
      summary:
        "Professional-level certification for designing, developing, and managing robust, secure, scalable, and dynamic solutions on Google Cloud Platform.",
      status: "completed",
      credentialUrl: "https://cloud.google.com/certification",
    },
    {
      providerName: "Microsoft",
      providerLogo: "/assets/logos/microsoft.svg",
      title: "Microsoft Certified: Azure Developer Associate",
      date: new Date("2024-01-10"),
      summary:
        "Certification validating skills in designing, building, testing, and maintaining cloud applications and services on Microsoft Azure.",
      status: "in-progress",
    },
    {
      providerName: "Kubernetes",
      providerLogo: "/assets/logos/kubernetes.svg",
      title: "Certified Kubernetes Administrator (CKA)",
      date: new Date("2023-08-05"),
      summary:
        "Demonstrates proficiency in Kubernetes administration including cluster architecture, installation, configuration, and troubleshooting.",
      status: "completed",
      credentialUrl: "https://www.cncf.io/certification/cka/",
    },
  ])

  // Sort certifications by date (latest first)
  sortedCertifications = computed(() => {
    return [...this.certifications()].sort((a, b) => b.date.getTime() - a.date.getTime())
  })

  constructor(private themeService: ThemeService) {}

  formatDate(date: Date): string {
    return date.toLocaleDateString("en-US", { year: "numeric", month: "long" })
  }
}
