import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioComponent } from './portfolio.component';
import {StockLatestPriceResponse, StockService} from "../stock.service";
import {instance, mock, when} from "ts-mockito";
import {Observable, of} from "rxjs";
import {NotificationService} from "../notification.service";
import {PortfolioService} from "../portfolio.service";

describe('PortfolioComponent (Unit test)', () => {
  let component: PortfolioComponent;
  let notificationService = mock(NotificationService);
  let stockService = mock(StockService);
  let portfolioService = mock(PortfolioService);

  beforeEach(async(() => {
    component = new PortfolioComponent(instance(notificationService), instance(stockService), instance(portfolioService));
  }));

  it('ngOnInit', () => {
    component.ngOnInit();

    expect(component.portfolioRows.length).toBe(2);
  });

  it('refreshPortfolioPrices', () => {
    let response0 = new StockLatestPriceResponse();
    response0.latestPrice = 1.23;
    when(stockService.getStockLatestPrice("AAPL")).thenReturn(of(response0));
    let response1 = new StockLatestPriceResponse();
    response1.latestPrice = 2.34;
    when(stockService.getStockLatestPrice("GOOG")).thenReturn(of(response1));

    component.ngOnInit();
    component.refreshPortfolioPrices();

    expect(component.portfolioRows.length).toBe(2);
    expect(component.portfolioRows[0].latestPrice).toBe(1.23);
    expect(component.portfolioRows[1].latestPrice).toBe(2.34);
  });
});
