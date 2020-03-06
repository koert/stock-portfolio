import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import * as moment from 'moment';
import {UrlQuery} from "./common/UrlQuery";
import {ConfigService} from "./common/config.service";

export class StockLatestPriceResponse {
  symbol: string;
  latestPrice: number;
}

export class PriceHistoryQuote {
  date: Date;
  closePrice: number;
}

export class StockPriceHistoryResponse {
  symbol: string;
  quotes: PriceHistoryQuote[];
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

  constructor(private http: HttpClient, private configService: ConfigService) {

  }

  getStockLatestPrice(symbol: string): Observable<StockLatestPriceResponse> {
    return this.http.get<StockLatestPriceResponse>(this.configService.makeServiceUrl(`/prices/${symbol}/latest`));
  }

  getPriceHistory(symbol: string, startDate: Date, endDate: Date, interval: string): Observable<StockPriceHistoryResponse> {
    let query = new UrlQuery();
    query.add("startDate", moment(startDate).format("YYYY-MM-DD"));
    query.add("endDate", moment(endDate).format("YYYY-MM-DD"));
    query.add("interval", interval);
    return this.http.get<StockPriceHistoryResponse>(this.configService.makeServiceUrl(`/prices/${symbol}/history` + query.buildQuery()));
  }

  searchStock(keyword: string): Observable<StockSearchResponse> {
    return this.http.get<StockSearchResponse>(this.configService.makeServiceUrl(`/stocks/search?keyword=${keyword}`));
  }

}
