import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {Observable} from "rxjs";
import {map, tap} from "rxjs/operators";
import * as jwt_decode from "jwt-decode";
import {ConfigService} from "../common/config.service";

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

export class LoginResponse {
  jwt: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  static readonly authorizationKey = "authorization";
  static readonly jwtExpiration = "jwtExpiration";

  private redirectUrl: string;

  constructor(private http: HttpClient, private configService: ConfigService) {
  }

  login(credentials: Credentials): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(this.configService.makeLoginServiceUrl("/security/login"), credentials, {observe: "response"})
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

  private static recordJwt(response: HttpResponse<LoginResponse>): void {
    let authorizationHeader = response.headers.get(LoginService.authorizationKey);
    let jwtToken = authorizationHeader;
    if (jwtToken) {
      if (jwtToken.startsWith("Bearer ")) {
        jwtToken = jwtToken.substring("Bearer ".length);
      }
    } else {
      jwtToken = response.body.jwt;
    }
    let decodedToken: JwtToken = jwt_decode(jwtToken);
    if (decodedToken) {
      localStorage.setItem(LoginService.authorizationKey, authorizationHeader);
      let expirationTimestamp = decodedToken.exp * 1000; // token expiration is in seconds (UTC), we use milliseconds
      localStorage.setItem(LoginService.jwtExpiration, expirationTimestamp.toString());
    }
  }
}
