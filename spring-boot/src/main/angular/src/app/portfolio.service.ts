import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {StockLatestPriceResponse} from "./stock.service";

export class StockPosition {
  symbol: string;
  amount: number;
  buyPrice: number;
  buyDate: Date;
  latestPrice: number;
  latestDate: Date;
}

export class Portfolio {
  positions: StockPosition[];
}

@Injectable({
  providedIn: 'root'
})
export class PortfolioService {

  constructor(private http: HttpClient) {
  }

  retrieve(): Observable<Portfolio> {
    return this.http.get<Portfolio>("portfolio/positions");
  }

  save(portfolio: Portfolio): Observable<void> {
    return this.http.put<void>("/portfolio/positions", portfolio);
  }

}
