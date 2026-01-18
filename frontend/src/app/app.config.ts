import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter, withInMemoryScrolling } from '@angular/router';

import { routes } from './app.routes';
import { MarkdownModule } from 'ngx-markdown';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { LucideAngularModule } from 'lucide-angular';
import {
  Github,
  Linkedin,
  Mail,
  Phone,
  Globe,
  Loader,
  ExternalLink,
  Menu,
  X
} from 'lucide-angular';
export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    importProvidersFrom(HttpClientModule),
    importProvidersFrom(
      MarkdownModule.forRoot({ loader: HttpClient })
    ),
    provideRouter(
      routes,
      withInMemoryScrolling({
        scrollPositionRestoration: 'top',
        anchorScrolling: 'enabled'
      })
    ),
     importProvidersFrom(
      LucideAngularModule.pick({
        Github,
        Linkedin,
        Mail,
        Phone,
        Globe,
        Loader,
        ExternalLink,
        Menu,
        X
      })
    )
  ]
};
