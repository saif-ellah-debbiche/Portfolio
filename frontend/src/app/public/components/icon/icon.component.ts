import { Component, computed, Input } from '@angular/core';
import { DynamicIcon } from '../../../core/models/dynamic-icon.module';
import { CommonModule } from '@angular/common';
import { ThemeService } from '../../../core/services/theme/theme.service';
import { LucideAngularModule } from 'lucide-angular';

@Component({
  selector: 'app-icon',
  imports: [CommonModule,LucideAngularModule],
  templateUrl: './icon.component.html',
  styleUrl: './icon.component.scss'
})
export class IconComponent {
 @Input() icon?: DynamicIcon;
  @Input() size = 20;
  @Input() imgClass = 'w-5 h-5';
  @Input() defaultIcon = 'mail';

  constructor(private themeService: ThemeService) {}

  resolvedImage = computed(() => {
    if (!this.icon) return null;

    if ( this.themeService.isDarkMode() && this.icon.darkLink) {
      return this.icon.darkLink;
    }

    if (!this.themeService.isDarkMode()&& this.icon.lightLink) {
      return this.icon.lightLink;
    }

    return null;
  });
}
