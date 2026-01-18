import { Certification } from "../models/certificate.model"
import { Education } from "../models/education.model"
import { Project } from "../models/project.model";
import { PortfolioOwner } from "../models/user.model";

interface SiteData{
projects:Project[];
owner:PortfolioOwner;
education:Education[];
certifications:Certification[]
}



export const SITE_DATA : SiteData={

     owner : {
    id:1,
    firstName: "Saif Ellah",
    lastName: "Debbiche",
    image: "assets/images/ownerImage.png",
    bio: "Aspiring Cloud Engineer | Computer Science Graduate | Engineering Student.",
    email: "saifellah.debbiche@esprit.tn",
    contacts: [
      {
        name: "GitHub",
        icon:{
        darkLink: "https://www.pngmart.com/files/23/Github-Logo-Transparent-PNG.png",
        lightLink: "assets/icons/github-light.png",
        svgPath:"M12 0c-6.626 0-12 5.373-12 12 0 5.302 3.438 9.8 8.207 11.387.599.111.793-.261.793-.577v-2.234c-3.338.726-4.033-1.416-4.033-1.416-.546-1.387-1.333-1.756-1.333-1.756-1.089-.745.083-.729.083-.729 1.205.084 1.839 1.237 1.839 1.237 1.07 1.834 2.807 1.304 3.492.997.107-.775.418-1.305.762-1.604-2.665-.305-5.467-1.334-5.467-5.931 0-1.311.469-2.381 1.236-3.221-.124-.303-.535-1.524.117-3.176 0 0 1.008-.322 3.301 1.23.957-.266 1.983-.399 3.003-.404 1.02.005 2.047.138 3.006.404 2.291-1.552 3.297-1.23 3.297-1.23.653 1.653.242 2.874.118 3.176.77.84 1.235 1.911 1.235 3.221 0 4.609-2.807 5.624-5.479 5.921.43.372.823 1.102.823 2.222v 3.293c0 .319.192.694.801.576 4.765-1.589 8.199-6.086 8.199-11.386 0-6.627-5.373-12-12-12z"
        },
       
        redirectLink: "https://github.com/saif-ellah-debbiche",
      },
      {
        name: "LinkedIn",
        icon:{
        darkLink: "https://cdn-icons-png.flaticon.com/512/717/717416.png",
        lightLink: "assets/icons/linkedin-light.png",
        svgPath:"M20.447 20.452h-3.554v-5.569c0-1.328-.475-2.236-1.986-2.236-1.081 0-1.722.731-2.004 1.435-.103.249-.129.597-.129.945v5.425h-3.554s.05-8.797 0-9.714h3.554v1.375c.427-.659 1.191-1.595 2.897-1.595 2.117 0 3.704 1.385 3.704 4.362v5.572zM5.337 8.855c-1.144 0-1.915-.762-1.915-1.715 0-.953.77-1.715 1.97-1.715 1.197 0 1.911.762 1.927 1.715 0 .953-.73 1.715-1.982 1.715zm1.582 11.597H3.635V9.538h3.284v10.914zM22.225 0H1.771C.792 0 0 .774 0 1.729v20.542C0 23.227.792 24 1.771 24h20.451C23.2 24 24 23.227 24 22.271V1.729C24 .774 23.2 0 22.225 0z"
        },
        redirectLink: "https://www.linkedin.com/in/saif-ellah-debbiche",

      },
      {
        name: "GitLab",
        icon:{
        darkLink: "https://iconape.com/wp-content/png_logo_vector/cib-gitlab.png",
        lightLink: "assets/icons/gitlab-logo.png",
        svgPath:"M22.545 13.239l-2.165-6.665a.608.608 0 00-1.155 0l-1.46 4.492H6.235l-1.46-4.492a.608.608 0 00-1.155 0L1.455 13.24a.606.606 0 00.22.678l10.325 7.504 10.325-7.504a.606.606 0 00.22-.679z"
        },
        redirectLink: "https://gitlab.com/saifellah.debbiche",

      },
    ],
  },
    projects:[
      {
    id: 1,
    title: "Accompagnement France - Full-Stack Platform",
    images: [
       "assets/images/accompagnement_france_admin.png",
        "assets/images/accompagnement_france_client.png",
    ],
    startDate: new Date("2025-09-01"),
    endDate: new Date("2026-01-01"), // Current ongoing/recent project
    summary:
      "A comprehensive freelance management platform featuring a dedicated client portal and an administrative back-office. Developed as a monorepo with high-performance routing and automated deployment.",
    technologies: ["Spring Boot", "Angular", "GitHub Actions", "JWT", "PostgreSQL"],
    duration: "4 months",
  },
        {
      id: 2,
      title: "Secure Budget Management System with DevOps Integration",
      images: [
        "assets/images/manager-dashboard.png",
        "assets/images/admin-dashboard.png",
        "assets/images/cfo-dashboard.png",
      ],
      startDate: new Date("2024-01-15"),
      endDate: new Date("2024-06-15"),
      summary:
        "A secure corporate application for budget tracking. Features advanced security protocols including digital certificate authentication (.p12) and TOTP Multi-Factor Authentication.",
    technologies: ["Spring Boot", "Angular", "Kubernetes", "GitLab CI/CD", "Docker"],
      duration:"6 months",
      
    },
    {
      id: 3,
      title: "AI Infrastructure Compliance Agent",
    images: [
        "assets/images/project_AI-application_creation.png",
        "assets/images/project_AI-results.png",
      ],
    startDate: new Date("2024-07-01"),
    endDate: new Date("2024-09-01"),
    summary:
      "An intelligent system designed to monitor infrastructure compliance. It uses an AI agent to detect configuration drifts and ensure alignment with the CMDB automatically.",
    technologies: ["Python", "Spring Boot", "Docker", "Kubernetes", "AI Agent"],
    duration: "3 months",
    },
   {
    id: 4,
    title: "Private Cloud Infrastructure",
    images: [ "assets/images/private_cloud.png"],
    startDate: new Date("2023-02-01"),
    endDate: new Date("2023-05-01"),
    summary:
      "Building a private IaaS/PaaS cloud environment to manage distributed solutions, featuring automated provisioning and resource scaling.",
    technologies: ["OpenStack", "Ansible", "Kubernetes", "Vagrant"],
    duration: "3 months",
  }
    ],
 
    education : [
    {
      universityName: "Higher School of Engineering and Technologies – ESPRIT",
      logo: "assets/universities/esprit-logo.png",
      degree: "Master of Engineering in Software Engineering – IT Architecture and Cloud Computing",
      status: "completed",
      startDate: "2022",
      endDate: "2025",
      description: "Specialized in Devops",
    },
    {
      universityName: "Higher Institute of Applied Sciences and Technology of Mateur – ISSAT Mateur",
      logo: "assets/universities/issat-mateur-logo.png",
      degree: "Bachelor’s degree in computer science",
      status: "completed",
      startDate: "2019",
      endDate: "2022",
      description: "Specialized in Software Development",
    },
    {
      universityName: "High School Farhat Hached Makthar",
      logo: "assets/universities/farhat-hached-lyce-logo.jpg",
      degree: "High school diploma in Mathematics",
      status: "completed",
      startDate: "2018",
      endDate: "2019",
      description: "Research focus on Distributed Systems and Cloud Computing",
    },
  ],
    certifications : [
]
}