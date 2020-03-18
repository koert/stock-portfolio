import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioComponent } from './portfolio.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {PanelModule} from "primeng/panel";
import {instance, mock, when} from "ts-mockito";
import {StockLatestPriceResponse, StockService} from "../stock.service";
import {FormsModule} from "@angular/forms";
import {TableModule} from "primeng/table";
import {CalendarModule} from "primeng/calendar";
import {of} from "rxjs";
import {OverlayPanelModule} from "primeng/overlaypanel";
import {PriceDialogComponent} from "./price-dialog/price-dialog.component";
import {ChartModule} from "primeng/chart";
import {SelectButtonModule} from "primeng/primeng";
import {NotificationService} from "../notification.service";
import {PortfolioService} from "../portfolio.service";

describe('PortfolioComponent', () => {
  let component: PortfolioComponent;
  let fixture: ComponentFixture<PortfolioComponent>;
  let notificationService = mock(NotificationService);
  let stockService = mock(StockService);
  let portfolioService = mock(PortfolioService);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [BrowserModule, BrowserAnimationsModule, FormsModule,
        ButtonModule, CalendarModule, ChartModule, DialogModule, DropdownModule, OverlayPanelModule,
        PanelModule, SelectButtonModule, TableModule],
      declarations: [ PortfolioComponent, PriceDialogComponent ],
      providers: [
        {provide: NotificationService, useValue: instance(notificationService)},
        {provide: StockService, useValue: instance(stockService)},
        {provide: PortfolioService, useValue: instance(portfolioService)}
      ],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PortfolioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();

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
