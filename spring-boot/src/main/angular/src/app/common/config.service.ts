import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  private priceServiceUrl: string;

  constructor() {
    this.priceServiceUrl = environment.priceServiceUrl;
  }

  makeServiceUrl(endpoint: string) {
    let url = this.priceServiceUrl;
    if (endpoint.startsWith("/")) {
      url = url + endpoint;
    } else {
      url = url + "/" + endpoint;
    }
    return url;
  }

  makePriceServiceUrl(endpoint: string) {
    let url: string;
    if (endpoint.startsWith("/")) {
      url = this.priceServiceUrl + endpoint;
    } else {
      url = this.priceServiceUrl + "/" + endpoint;
    }
    return url;
  }

  makeStockServiceUrl(endpoint: string) {
    let url: string;
    if (endpoint.startsWith("/")) {
      url = this.priceServiceUrl + endpoint;
    } else {
      url = this.priceServiceUrl + "/" + endpoint;
    }
    return url;
  }

}
