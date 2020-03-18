import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PriceDialogComponent } from './price-dialog.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {PanelModule} from "primeng/panel";
import {TableModule} from "primeng/table";
import {PortfolioComponent} from "../portfolio.component";
import {StockService} from "../../stock.service";
import {instance, mock} from "ts-mockito";
import {NotificationService} from "../../notification.service";
import {SelectButtonModule} from "primeng/primeng";
import {ChartModule} from "primeng/chart";
import {OverlayPanelModule} from "primeng/overlaypanel";

describe('PriceDialogComponent', () => {
  let stockService = mock(StockService);
  let notificationService = mock(NotificationService);
  let component: PriceDialogComponent;
  let fixture: ComponentFixture<PriceDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [BrowserModule, BrowserAnimationsModule, FormsModule,
        ButtonModule, CalendarModule, ChartModule, DialogModule,
        DropdownModule, OverlayPanelModule, PanelModule, SelectButtonModule, TableModule],
      declarations: [ PriceDialogComponent ],
      providers: [
        {provide: NotificationService, useValue: instance(notificationService)},
        {provide: StockService, useValue: instance(stockService)}
      ],

    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PriceDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
