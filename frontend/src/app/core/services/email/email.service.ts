import { Injectable } from '@angular/core';
import emailjs from '@emailjs/browser';
import { ContactInfo } from '../../models/contact-info.module';

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  PUBLIC_KEY = "stkH8ABzwV15Vs1LM";
  saif_service_id='service_wmjetop';
  template_id='template_6ao4jqf';
  options={
    publicKey: this.PUBLIC_KEY,
  // Do not allow headless browsers
  blockHeadless: true,
  blockList: {
    // Block the suspended emails
    list: ['foo@emailjs.com', 'bar@emailjs.com'],
    // The variable contains the email address
    watchVariable: 'userEmail',
  },
  limitRate: {
    // Set the limit rate for the application
    id: 'app',
    // Allow 1 request per 10s
    throttle: 10000,
  },
  }
  constructor() { 
    emailjs
    .init(this.options);
  }

  sendEmail(contactInfo:ContactInfo){
  return emailjs.send(this.saif_service_id,this.template_id,{
  from_name: contactInfo.fromName,
  reply_to: contactInfo.fromEmail,
  message: contactInfo.message
} )
  }
}
