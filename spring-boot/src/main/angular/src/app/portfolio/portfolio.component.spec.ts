import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PortfolioComponent } from './portfolio.component';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {PanelModule} from "primeng/panel";
import {instance, mock} from "ts-mockito";
import {StockService} from "../stock.service";
import {FormsModule} from "@angular/forms";
import {TableModule} from "primeng/table";
import {CalendarModule} from "primeng/calendar";

describe('PortfolioComponent', () => {
  let component: PortfolioComponent;
  let fixture: ComponentFixture<PortfolioComponent>;
  let stockService = mock(StockService);

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [BrowserModule, BrowserAnimationsModule, FormsModule,
        ButtonModule, CalendarModule, DialogModule, DropdownModule, PanelModule, TableModule],
      declarations: [ PortfolioComponent ],
      providers: [
        {provide: StockService, useValue: instance(stockService)}
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
  });
});
