import { Component, OnInit } from '@angular/core';
import {SelectItem} from "primeng/api";
import {NotificationService} from "../../notification.service";
import {StockService} from "../../stock.service";
import * as moment from 'moment';

class Period {
  amount: number;
  unit: "year" | "month" | "week";
  interval: string;

  constructor(amount: number, unit: "year" | "month" | "week", interval: string) {
    this.amount = amount;
    this.unit = unit;
    this.interval = interval;
  }
}

@Component({
  selector: 'price-dialog',
  templateUrl: './price-dialog.component.html',
  styleUrls: ['./price-dialog.component.scss']
})
export class PriceDialogComponent implements OnInit {
  static readonly fiveYears = new Period(5, "year", "MONTHLY");
  static readonly oneYear = new Period(1, "year", "WEEKLY");
  static readonly sixMonths = new Period(6, "month", "WEEKLY");
  static readonly threeMonths = new Period(3, "month", "DAILY");
  static readonly oneMonth = new Period(1, "month", "DAILY");
  static readonly oneWeek = new Period(1, "week", "DAILY");

  visible: boolean = false;
  symbol: string;
  name: string;
  data: any;
  rangeSelectionItems: SelectItem[] = [
    {label: "5 years", value: PriceDialogComponent.fiveYears},
    {label: "1 year", value: PriceDialogComponent.oneYear},
    {label: "6 months", value: PriceDialogComponent.sixMonths},
    {label: "3 months", value: PriceDialogComponent.threeMonths},
    {label: "1 month", value: PriceDialogComponent.oneMonth},
    {label: "1 week", value: PriceDialogComponent.oneWeek},
  ];
  rangeSelection: Period = PriceDialogComponent.oneYear;

  constructor(private notificationService: NotificationService, private stockService: StockService) { }

  ngOnInit() {
  }

  show(symbol: string, name: string): void {
    this.symbol = symbol;
    this.name = name;
    this.showChart();
    this.visible = true;
  }

  selectData(event) {
    // this.notificationService.info({'Data Selected', this.data.datasets[event.element._datasetIndex].data[event.element._index]});
  }

  onRangeChange($event: any) {
    this.showChart();
  }

  private showChart(): void {
    let startDate = moment().subtract(this.rangeSelection.amount, this.rangeSelection.unit).toDate();
    let endDate = moment().toDate();
    this.stockService.getPriceHistory(this.symbol, startDate, endDate, this.rangeSelection.interval).subscribe(priceHistory => {
      let labels: string[] = [];
      let data: number[] = [];
      for (let quote of priceHistory.quotes) {
        labels.push(moment(quote.date).format("YYYY-MM-DD"));
        data.push(quote.closePrice);
      }
      this.data = {
        labels: labels,
        datasets: [
          {
            label: "Closing price",
            data: data,
            fill: false,
            borderColor: '#4bc0c0'
          }
        ]
      }
    });

  }
}
