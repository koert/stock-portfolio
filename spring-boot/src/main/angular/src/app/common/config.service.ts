import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor() { }

  makeServiceUrl(endpoint: string) {
    let url = "";
    if (endpoint.startsWith("/")) {
      url = url + endpoint;
    } else {
      url = url + "/" + endpoint;
    }
    return url;
  }

}
