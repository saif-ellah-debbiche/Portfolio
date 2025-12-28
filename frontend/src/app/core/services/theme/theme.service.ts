import { effect, Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  isDarkMode = signal<boolean>(false)

  constructor() {
    // Check for saved theme preference or default to system preference
    const savedTheme = localStorage.getItem("theme")
    const prefersDark = window.matchMedia("(prefers-color-scheme: dark)").matches

    this.isDarkMode.set(savedTheme === "dark" || (!savedTheme && prefersDark))

    // Apply theme on initialization
    effect(() => {
      this.applyTheme()
    })
  }

  toggleTheme(): void {
    this.isDarkMode.update((value) => !value)
  }

  private applyTheme(): void {
    const theme = this.isDarkMode() ? "dark" : "light"
    document.documentElement.setAttribute("data-theme", theme)
    localStorage.setItem("theme", theme)
  }
}
