import { TestBed, async, inject } from '@angular/core/testing';

import { AuthorizationGuard } from './authorization-guard.service';
import {instance, mock} from "ts-mockito";
import {NotificationService} from "../notification.service";
import {Router} from "@angular/router";
import {LoginService} from "./login.service";
import {StockService} from "../stock.service";
import {PortfolioService} from "../portfolio.service";

describe('AuthorizationGuard', () => {
  let router = mock(Router);
  let loginService = mock(LoginService);
  let notificationService = mock(NotificationService);

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthorizationGuard,
        {provide: Router, useValue: instance(router)},
        {provide: LoginService, useValue: instance(loginService)},
        {provide: NotificationService, useValue: instance(notificationService)}
      ],
    });
  });

  it('should ...', inject([AuthorizationGuard], (guard: AuthorizationGuard) => {
    expect(guard).toBeTruthy();
  }));
});
