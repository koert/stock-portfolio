import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

export class StockLatestPriceResponse {
  symbol: string;
  latestPrice: number;
}

export class StockMatch {
  symbol: string;
  name: string;
  currency: string;
}

export class StockSearchResponse {
  matches: StockMatch[];
}

@Injectable({
  providedIn: 'root'
})
export class StockService {

  constructor(private http: HttpClient) {

  }

  getStockLatestPrice(symbol: string): Observable<StockLatestPriceResponse> {
    return this.http.get<StockLatestPriceResponse>(`/prices/${symbol}/latest`);
  }

  searchStock(keyword: string): Observable<StockSearchResponse> {
    return this.http.get<StockSearchResponse>(`/stocks/search?keyword=${keyword}`);
  }
}
