import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import { Observable } from 'rxjs';
import {LoginService} from "./login.service";
import {NotificationService} from "../notification.service";

@Injectable({
  providedIn: 'root'
})
export class AuthorizationGuard implements CanActivate {

  constructor(private router: Router, private loginService: LoginService, private notificationService: NotificationService) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    let result = this.loginService.isLoggedIn();
    if (!result) {
      this.notificationService.error("Login", "Your session has expired, log in again.");
      this.loginService.registerRedirectUrl(state.url);
      this.router.navigate(["/login"]);
    }

    return true;
  }
}
