import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {map, tap} from "rxjs/operators";

export class Credentials {
  userName: string;
  password: string;

  constructor(userName: string, password: string) {
    this.userName = userName;
    this.password = password;
  }
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  static readonly authorizationKey = "authorization";

  private redirectUrl: string;

  constructor(private http: HttpClient) {
  }

  login(credentials: Credentials): Observable<void> {
    return this.http.post<void>("/login", credentials, {observe: "response"})
      .pipe(
        tap(LoginService.recordJwt),
        map(response => response.body)
      );
  }

  isLoggedIn(): boolean {
    return localStorage.getItem(LoginService.authorizationKey) != null;
  }

  registerRedirectUrl(url: string) {
    this.redirectUrl = url;
  }

  getRedirectUrl(): string {
    return this.redirectUrl;
  }

  private static recordJwt(response: HttpResponse<void>): void {
    let authorizationHeader = response.headers.get(LoginService.authorizationKey);
    localStorage.setItem(LoginService.authorizationKey, authorizationHeader);
  }
}
