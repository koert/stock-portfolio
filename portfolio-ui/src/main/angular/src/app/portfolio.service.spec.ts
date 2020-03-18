import { TestBed } from '@angular/core/testing';

import { PortfolioService } from './portfolio.service';
import {instance, mock} from "ts-mockito";
import {HttpClient} from "@angular/common/http";

describe('PortfolioService', () => {
  let httpClient = mock(HttpClient);

  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      {provide: HttpClient, useValue: instance(httpClient)},
    ],
  }));

  it('should be created', () => {
    const service: PortfolioService = TestBed.get(PortfolioService);
    expect(service).toBeTruthy();
  });
});
