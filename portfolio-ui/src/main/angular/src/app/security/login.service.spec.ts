import { TestBed } from '@angular/core/testing';

import { LoginService } from './login.service';
import {instance, mock} from "ts-mockito";
import {NotificationService} from "../notification.service";
import {HttpClient} from "@angular/common/http";
import {ConfigService} from "../common/config.service";
import {StockService} from "../stock.service";
import {PortfolioService} from "../portfolio.service";

describe('LoginService', () => {
  let httpClient = mock(HttpClient);
  let configService = mock(ConfigService);

  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      {provide: HttpClient, useValue: instance(httpClient)},
      {provide: ConfigService, useValue: instance(configService)},
    ],

  }));

  it('should be created', () => {
    const service: LoginService = TestBed.get(LoginService);
    expect(service).toBeTruthy();
  });
});
