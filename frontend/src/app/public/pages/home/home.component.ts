import { Component } from '@angular/core';
import {  RouterModule } from '@angular/router';
import { IntroSectionComponent } from '../../layout/intro-section/intro-section.component';
import { ProjectsComponent } from '../../layout/projects/projects.component';
import { EducationComponent } from '../../layout/education/education.component';
import { CertificationComponent } from '../../layout/certification/certification.component';
import { ContactComponent } from "../../layout/contact/contact.component";


@Component({
  selector: 'app-home',
 imports: [RouterModule, IntroSectionComponent, ProjectsComponent, EducationComponent, CertificationComponent, ContactComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

}
