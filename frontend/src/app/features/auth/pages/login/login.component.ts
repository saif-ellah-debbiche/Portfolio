import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { ThemeService } from '../../../../core/services/theme/theme.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../core/services/auth/auth.service';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
private router = inject(Router)
  themeService = inject(ThemeService)
  authService = inject(AuthService)

  email = ""
  password = ""
  isLoading = false
  errorMessage = ""
  showPassword = false

  onSubmit() {
    if (!this.email || !this.password) {
      this.errorMessage = "Please fill in all fields"
      return
    }

    this.isLoading = true
    this.errorMessage = ""

    // Simulate login delay
    this.authService.login(this.email,this.password).subscribe(
      {
      next:response=> {
      this.isLoading = false
      // TODO: Add actual authentication logic here
      console.log("Login attempt with:", { email: this.email, password: this.password })
      

      this.router.navigate(["/owner"])
    },
    error:(err:Error)=>{
      console.log(err);
      this.errorMessage = err.message;
    
    }
   });
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword
  }
}
