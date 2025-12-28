import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationResponse } from '../../models/auth-response.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  readonly authUrl = "http://localhost:8081/auth"
  accessToken:string|null = null;

  constructor(private http:HttpClient) { }

  login(email:string,password:string):Observable<AuthenticationResponse>{
    return this.http.post<AuthenticationResponse>(this.authUrl,{
      email,
      password
    })
  }
   setAccessToken(token: string) {
    this.accessToken = token;
  }

  getAccessToken() {
    return this.accessToken;
  }

  clearAccessToken() {
    this.accessToken = null;
  }
}
