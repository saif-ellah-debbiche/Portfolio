import { Component } from '@angular/core';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { SITE_DATA } from '../../../core/config/siteData';
interface SocialLink{
  name: string;
  href: string;
  icon: string;
  svgPath:string
}
@Component({
  selector: 'app-footer',
  imports: [],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.scss'
})
export class FooterComponent {
  currentYear = new Date().getFullYear()
  ownerContacts =SITE_DATA.owner.contacts;
  socialLinks:SocialLink[] =[]; 

  

  constructor(public themeService: ThemeService) {
  this.socialLinks = this.ownerContacts.map(item => ({
  name: item.name,
  href: item.redirectLink,
  icon: item.name.toLowerCase(),
  svgPath:item.icon.svgPath
})); 
  }
}
