import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {map, tap} from "rxjs/operators";
import * as jwt_decode from "jwt-decode";

export class Credentials {
  userName: string;
  password: string;

  constructor(userName: string, password: string) {
    this.userName = userName;
    this.password = password;
  }
}

interface JwtToken {
  sub: string;
  exp: number;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  static readonly authorizationKey = "authorization";
  static readonly jwtExpiration = "jwtExpiration";

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
    let authorization = localStorage.getItem(LoginService.authorizationKey);
    let expiration = localStorage.getItem(LoginService.jwtExpiration);
    let loggedIn = false;
    if (authorization && expiration) {
      let expirationTimestamp = parseInt(expiration);
      if (new Date().valueOf() < expirationTimestamp) {
        loggedIn = true;
      }
    }
    return loggedIn;
  }

  registerRedirectUrl(url: string) {
    this.redirectUrl = url;
  }

  getRedirectUrl(): string {
    return this.redirectUrl;
  }

  private static recordJwt(response: HttpResponse<void>): void {
    let authorizationHeader = response.headers.get(LoginService.authorizationKey);
    let jwtToken = authorizationHeader;
    if (jwtToken.startsWith("Bearer ")) {
      jwtToken = jwtToken.substring("Bearer ".length);
    }
    let decodedToken: JwtToken = jwt_decode(jwtToken);
    if (decodedToken) {
      localStorage.setItem(LoginService.authorizationKey, authorizationHeader);
      let expirationTimestamp = decodedToken.exp * 1000; // token expiration is in seconds (UTC), we use milliseconds
      localStorage.setItem(LoginService.jwtExpiration, expirationTimestamp.toString());
    }
  }
}
