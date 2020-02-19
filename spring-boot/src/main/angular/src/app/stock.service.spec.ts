import { TestBed } from '@angular/core/testing';

import { StockService } from './stock.service';
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {PanelModule} from "primeng/panel";
import {TableModule} from "primeng/table";
import {HttpClient} from "@angular/common/http";
import {instance, mock} from "ts-mockito";

describe('StockService', () => {
  let mockHttpClient = mock(HttpClient);

  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      {provide: HttpClient, useValue: instance(mockHttpClient)}
    ]
  }));

  it('should be created', () => {
    const service: StockService = TestBed.get(StockService);
    expect(service).toBeTruthy();
  });
});
