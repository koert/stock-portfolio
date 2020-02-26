import { Component, OnInit } from '@angular/core';
import {StockService} from "../stock.service";
import {HttpErrorResponse} from "@angular/common/http";
import * as moment from "moment";
import {CalculationUtil} from "../common/CalculationUtil";
import {Portfolio, PortfolioService, StockPosition} from "../portfolio.service";
import {NotificationService} from "../notification.service";

export class PortfolioRow {
  rowIndex: number;
  symbol: string;
  name: string;
  amount: number;
  currency: string;
  buyDate: Date;
  buyPrice: number;
  buyValue: number;
  latestDate: Date;
  latestPrice: number;
  latestValue: number;
  profit: number;
  annualizedProfit: number;

  static copyOf(row: PortfolioRow): PortfolioRow {
    let newRow = new PortfolioRow();
    newRow.symbol = row.symbol;
    newRow.name = row.name;
    newRow.amount = row.amount;
    newRow.currency = row.currency;
    newRow.buyDate = row.buyDate;
    newRow.buyPrice = row.buyPrice;
    newRow.buyValue = row.buyValue;
    newRow.latestDate = row.latestDate;
    newRow.latestPrice = row.latestPrice;
    newRow.latestValue = row.latestValue;
    return newRow;
  }

  static create(position: StockPosition): PortfolioRow {
    let newRow = new PortfolioRow();
    newRow.symbol = position.symbol;
    // newRow.name = position.name;
    newRow.amount = position.amount;
    // newRow.currency = position.currency;
    newRow.buyDate = position.buyDate;
    newRow.buyPrice = position.buyPrice;
    // newRow.buyValue = position.buyValue;
    newRow.latestDate = position.latestDate;
    newRow.latestPrice = position.latestPrice;
    // newRow.latestValue = position.latestValue;
    return newRow;
  }

  getStockPosition(): StockPosition {
    let position = new StockPosition();
    position.symbol = this.symbol;
    position.amount = this.amount;
    position.buyPrice = this.buyPrice;
    position.buyDate = this.buyDate;
    position.latestPrice = this.latestPrice;
    position.latestDate = this.latestDate;
    return position;
  }
}

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss']
})
export class PortfolioComponent implements OnInit {

  portfolioRows: PortfolioRow[];
  positionDialogVisible: boolean = false;
  selectedPortfolioRow: PortfolioRow;
  editPortfolioRow: PortfolioRow;

  constructor(private notificationService: NotificationService, private stockService: StockService,
              private portfolioService: PortfolioService) { }

  ngOnInit() {
    let row1 = new PortfolioRow();
    row1.rowIndex = 0;
    row1.symbol = "AAPL";
    row1.amount = 10;
    row1.currency = "USD";
    // row1.buyDate = ;
    row1.buyPrice = 12.34;
    row1.buyValue = row1.amount * row1.buyPrice;
    // row1.latestDate = ;
    // row1.latestPrice = 12.34;
    // row1.latestValue = row1.amount * row1.buyPrice;
    let row2 = new PortfolioRow();
    row2.rowIndex = 0;
    row2.symbol = "GOOG";
    row2.amount = 15;
    row2.currency = "USD";
    // row1.buyDate = ;
    row2.buyPrice = 123.45;
    row2.buyValue = row2.amount * row2.buyPrice;
    // row1.latestDate = ;
    // row1.latestPrice = 12.34;
    // row1.latestValue = row1.amount * row1.buyPrice;
    this.portfolioRows = [row1, row2];
  }

  refreshPortfolioPrices(): void {
    this.retrieveLatestPrice(this.portfolioRows, () => {
      this.calculateProfit();
    });
  }

  /**
   * Retrieve latest prices for stocks with keys.
   * @param keys List of stock keys.
   * @param stockMap Map with stocks, indexed with keys.
   * @param onCompleted Callback when retrieval is complete.
   */
  private retrieveLatestPrice(rows: PortfolioRow[], onCompleted: () => void) {
    if (rows.length > 0) {
      let row: PortfolioRow = rows[0];
      this.stockService.getStockLatestPrice(row.symbol).subscribe(response => {
        let date = moment().startOf("day").toDate()
        row.latestDate = date;
        row.latestPrice = response.latestPrice;
        row.latestValue = row.amount * row.latestPrice;
        this.retrieveLatestPrice(rows.slice(1), onCompleted);
      },
        (error: HttpErrorResponse) => {
          if (error.status === 404) {
            this.retrieveLatestPrice(rows.slice(1), onCompleted);
          } else {
            onCompleted();
          }
        },
        () => {}
        );
    } else {
      onCompleted();
    }
  }

  showPositionDialog(row: PortfolioRow) {
    this.positionDialogVisible = true;
    this.selectedPortfolioRow = row;
    this.editPortfolioRow = PortfolioRow.copyOf(row);
  }

  positionEditCancel() {
    this.positionDialogVisible = false;
  }

  positionEditOk() {
    let row: PortfolioRow = this.selectedPortfolioRow;
    if (!this.selectedPortfolioRow) {
      row = new PortfolioRow();
    }
    row.symbol = this.editPortfolioRow.symbol;
    row.amount = this.editPortfolioRow.amount;
    row.buyDate = this.editPortfolioRow.buyDate;
    row.buyPrice = this.editPortfolioRow.buyPrice;
    this.calculateProfitRow(row);
    if (!this.selectedPortfolioRow) {
      this.portfolioRows.push(row);
    }
    this.positionDialogVisible = false;
  }

  addPosition() {
    this.selectedPortfolioRow = null;
    this.editPortfolioRow = new PortfolioRow();
    this.positionDialogVisible = true;
  }

  retrieve() {
    this.portfolioService.retrieve().subscribe(portfolio => {
      this.portfolioRows = portfolio.positions.map(position => PortfolioRow.create(position));
    },
      error => this.notificationService.error("Portfolio", "Error"));
  }

  save() {
    let portfolio = new Portfolio();
    portfolio.positions = this.portfolioRows.map(row => row.getStockPosition());
    this.portfolioService.save(portfolio).subscribe(response => {
      this.notificationService.info("Portfolio", "Saved")
    },
      error => this.notificationService.error("Portfolio", "Error"));
  }

  private calculateProfit(): void {
    this.portfolioRows.forEach(row => this.calculateProfitRow(row));
  }

  private calculateProfitRow(row: PortfolioRow): void {
    row.buyValue = row.amount * row.buyPrice;
    row.latestValue = row.amount * row.latestPrice;
    row.profit = row.latestValue - row.buyValue;
    if (row.buyDate && row.latestDate) {
      row.annualizedProfit = CalculationUtil.calculateAnnualizedReturn(row.buyPrice, row.latestPrice, row.buyDate, row.latestDate);
    }
  }
}
