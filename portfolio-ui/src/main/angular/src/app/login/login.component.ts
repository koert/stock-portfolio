import {Component, OnDestroy, OnInit} from '@angular/core';
import {Credentials, LoginService} from "../security/login.service";
import {NotificationService} from "../notification.service";
import {SubSink} from "subsink";
import {Router} from "@angular/router";

/**
 * https://docs.aws.amazon.com/cognito/latest/developerguide/getting-started-with-cognito-user-pools.html
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {

  private subs = new SubSink();

  userName: string;
  password: string;

  constructor(private notificationService: NotificationService, private router: Router, private loginService: LoginService) {
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
  }

  login(): void {
    let credentials = new Credentials(this.userName, this.password);
    this.subs.add(this.loginService.login(credentials).subscribe(loginResponse => {
        this.notificationService.info("Login", "Login successful");
        let url = this.loginService.getRedirectUrl();
        if (!url) {
          url = "/portfolio";
        }
        this.router.navigate([url]);
      },
      error => {
        this.notificationService.error("Login", "Login failed");
      },
      () => {}
    ));
  }

}
