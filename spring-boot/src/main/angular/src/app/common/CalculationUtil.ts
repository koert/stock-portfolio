import * as moment from 'moment';

export class CalculationUtil {

  static calculateDirectReturn(buyPrice: number, sellPrice: number): number {
    let returnRatio = 0.0;
    returnRatio = (sellPrice - buyPrice) / buyPrice;
    return returnRatio;
  }

  static calculateAnnualizedReturn(buyPrice: number, sellPrice: number, buyDate: Date, sellDate: Date): number {
    let returnRatio = 0.0;
    let plainRatio = (sellPrice - buyPrice) / buyPrice;

    let numberOfDays = moment(sellDate).diff(moment(buyDate), "days");
    if (numberOfDays < 365) {
      returnRatio = plainRatio
    } else {
      let numberOfYears = numberOfDays / 365;
      returnRatio = Math.pow(plainRatio + 1, 1 / numberOfYears) - 1;
    }

    return returnRatio;
  }
}
