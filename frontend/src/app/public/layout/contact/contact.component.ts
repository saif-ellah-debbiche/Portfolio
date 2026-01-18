import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EmailService } from '../../../core/services/email/email.service';
import { ContactInfo } from '../../../core/models/contact-info.module';
import { CommonModule } from '@angular/common';
declare const grecaptcha: any;
@Component({
  selector: 'app-contact',
  imports: [ReactiveFormsModule,CommonModule],
  templateUrl: './contact.component.html',
  styleUrl: './contact.component.scss'
})
export class ContactComponent implements OnInit{
  contactForm!: FormGroup;
  submitting = false;
  successMessage = '';
  errorMessage = '';
  private siteKey = '6LcJrU4sAAAAAAU1PVrtWBuejt0VCmyiEXPYQvsP';
  private formLoadTime = 0;
  private readonly MIN_FILL_TIME = 3000;
  private readonly MAX_EMAILS_PER_HOUR = 3;
  private readonly RATE_LIMIT_KEY = 'email_sent_count';



  constructor(private fb: FormBuilder,private emaiService:EmailService) {
  }
 ngOnInit() {
   this.formLoadTime = Date.now();
    // Build the form
    this.contactForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      title: ['', Validators.required],
      message: ['', Validators.required],
      lastName:['']
    });
  }
  execute(action: string): Promise<string> {
    return new Promise((resolve, reject) => {
      grecaptcha.ready(() => {
        grecaptcha.execute(this.siteKey, { action })
          .then((token: string) => resolve(token))
          .catch((error: any) => reject(error));
      });
    });
  }
 private canSendEmail(): boolean {
    const data = localStorage.getItem(this.RATE_LIMIT_KEY);
    if (!data) return true;
    
    const { count, timestamp } = JSON.parse(data);
    const oneHour = 60 * 60 * 1000;
    
    if (Date.now() - timestamp > oneHour) {
      localStorage.removeItem(this.RATE_LIMIT_KEY);
      return true;
    }
    
    return count < this.MAX_EMAILS_PER_HOUR;
  }
   private incrementEmailCount(): void {
    const data = localStorage.getItem(this.RATE_LIMIT_KEY);
    
    if (!data) {
      localStorage.setItem(this.RATE_LIMIT_KEY, JSON.stringify({
        count: 1,
        timestamp: Date.now()
      }));
    } else {
      const { count, timestamp } = JSON.parse(data);
      localStorage.setItem(this.RATE_LIMIT_KEY, JSON.stringify({
        count: count + 1,
        timestamp
      }));
    }
  }
  private validateContent(): boolean {
    const formValues = this.contactForm.value;
    
    const suspiciousPatterns = [
      /<script/i,
      /javascript:/i,
      /<iframe/i,
      /\[url=/i,
      /http.*http/i,
      /<embed/i,
      /<object/i
    ];
    
    const allText = Object.values(formValues).join(' ');
    
    return !suspiciousPatterns.some(pattern => pattern.test(allText));
  }
  async send() {
     if (this.contactForm.get('lastName')?.value) return
    if (this.contactForm.invalid) {
      this.contactForm.markAllAsTouched();
      return;
    }

     const fillTime = Date.now() - this.formLoadTime;
    if (fillTime < this.MIN_FILL_TIME) {
      this.errorMessage = 'Please take your time filling the form';
      return;
    }
if (!this.canSendEmail()) {
      this.errorMessage = 'Too many requests. Please try again in an hour.';
      return;
    }

     if (!this.validateContent()) {
      this.errorMessage = 'Invalid content detected.';
      return;
    }

    this.submitting = true;
    this.successMessage = '';
    this.errorMessage = '';

    const formValues = this.contactForm.value;
    try {
      // 5. Get reCAPTCHA token
      const recaptchaToken = await this.execute('contact_form');
    // Send via EmailJS using exact template variable names
    const templateParams :ContactInfo= {
      fromName: formValues.name,
      fromEmail: formValues.email,
      title: formValues.title,
      message: formValues.message,
    };

    await this.emaiService.sendEmail(templateParams) 
    this.successMessage = 'Thank you! Your message has been sent successfully.';
      this.contactForm.reset();
      this.incrementEmailCount();
      this.formLoadTime = Date.now();
  }catch (error: any) {
      console.error('Error:', error);
      this.errorMessage = 'Failed to send message. Please try again later.';
    } finally {
      this.submitting = false;
    }
  }
}
