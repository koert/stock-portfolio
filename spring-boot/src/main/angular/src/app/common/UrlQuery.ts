
export class UrlQuery {
  private criteriaMap: {[key: string]:  string;} = {};

  add(key: string, value: any): void {
    if (typeof value === 'boolean') {
      if (value) {
        this.criteriaMap[key] = 'true';
      } else {
        this.criteriaMap[key] = 'false';
      }
    } else {
      if (value) {
        this.criteriaMap[key] = value;
      }
    }
  }

  buildQuery(): string {
    let parameters: string[] = [];
    for (let key in this.criteriaMap) {
      if (this.criteriaMap.hasOwnProperty(key)) {
        parameters.push(key + '=' + encodeURIComponent(this.criteriaMap[key]));
      }
    }

    let query: string;
    if (parameters.length > 0) {
      query = '?' + parameters.join('&');
    } else {
      query = '';
    }
    return query;
  }

}