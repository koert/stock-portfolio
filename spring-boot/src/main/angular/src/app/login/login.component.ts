import { Component, OnInit } from '@angular/core';

/**
 * https://docs.aws.amazon.com/cognito/latest/developerguide/getting-started-with-cognito-user-pools.html
 */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  userId: string;
  password: string;

  constructor() { }

  ngOnInit() {
  }

  login(): void {

  }

}
