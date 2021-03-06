import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {StockMatch, StockService} from "../stock.service";
import {HttpErrorResponse} from "@angular/common/http";
import * as moment from "moment";
import {CalculationUtil} from "../common/CalculationUtil";
import {Portfolio, PortfolioService, StockPosition} from "../portfolio.service";
import {NotificationService} from "../notification.service";
import {SubSink} from "subsink";
import {OverlayPanel} from "primeng/overlaypanel";
import {PriceDialogComponent} from "./price-dialog/price-dialog.component";

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
export class PortfolioComponent implements OnInit, OnDestroy {

  @ViewChild("priceDialog", { static: false }) private priceDialog: PriceDialogComponent;

  private subs = new SubSink();

  portfolioRows: PortfolioRow[];
  positionDialogVisible: boolean = false;
  editPositionDialogVisible: boolean = false;
  selectedPortfolioRow: PortfolioRow;
  editPortfolioRow: PortfolioRow;
  symbolEntry: string;
  stockMatches: StockMatch[];

  constructor(private notificationService: NotificationService, private stockService: StockService,
              private portfolioService: PortfolioService) { }

  ngOnInit() {
    let row1 = new PortfolioRow();
    row1.rowIndex = 0;
    row1.symbol = "AAPL";
    row1.name = "Apple";
    row1.amount = 10;
    row1.currency = "USD";
    row1.buyDate = moment("2020-02-01").toDate();
    row1.buyPrice = 12.34;
    row1.buyValue = row1.amount * row1.buyPrice;
    // row1.latestDate = ;
    // row1.latestPrice = 12.34;
    // row1.latestValue = row1.amount * row1.buyPrice;
    let row2 = new PortfolioRow();
    row2.rowIndex = 0;
    row2.symbol = "GOOG";
    row2.name = "Alphabet";
    row2.amount = 15;
    row2.currency = "USD";
    row2.buyDate = moment("2020-01-15").toDate();
    row2.buyPrice = 123.45;
    row2.buyValue = row2.amount * row2.buyPrice;
    // row1.latestDate = ;
    // row1.latestPrice = 12.34;
    // row1.latestValue = row1.amount * row1.buyPrice;
    this.portfolioRows = [row1, row2];
  }

  ngOnDestroy(): void {
    this.subs.unsubscribe();
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
      this.subs.add(this.stockService.getStockLatestPrice(row.symbol).subscribe(response => {
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
        ));
    } else {
      onCompleted();
    }
  }

  showPositionDialog(row: PortfolioRow) {
    this.editPositionDialogVisible = true;
    this.selectedPortfolioRow = row;
    this.editPortfolioRow = PortfolioRow.copyOf(row);
  }

  showChartDialog(row: PortfolioRow) {
    // this.editChartDialogVisible = true;
    // this.selectedPortfolioRow = row;

    this.priceDialog.show(row.symbol, row.name);
  }

  positionDialogClose() {
    this.positionDialogVisible = false;
  }

  positionEditCancel() {
    this.editPositionDialogVisible = false;
  }

  positionEditOk() {
    let row: PortfolioRow = this.selectedPortfolioRow;
    if (!this.selectedPortfolioRow) {
      row = new PortfolioRow();
    }
    row.symbol = this.editPortfolioRow.symbol;
    row.name = this.editPortfolioRow.name;
    row.currency = this.editPortfolioRow.currency;
    row.amount = this.editPortfolioRow.amount;
    row.buyDate = this.editPortfolioRow.buyDate;
    row.buyPrice = this.editPortfolioRow.buyPrice;
    this.calculateProfitRow(row);
    if (!this.selectedPortfolioRow) {
      this.portfolioRows.push(row);
    }
    this.editPositionDialogVisible = false;
  }

  showSymbolEntryOverlay() {
    this.symbolEntry = this.editPortfolioRow.symbol;
  }

  symbolSearch() {
    if (this.symbolEntry) {
      this.stockService.searchStock(this.symbolEntry).subscribe(searchResponse => {
        this.stockMatches = searchResponse.matches;
      });
    } else {
      this.notificationService.error("Add position", "Enter a symbol or name");
    }
  }

  symbolSearchSelectRow(row: StockMatch, overlay: OverlayPanel) {
    this.symbolEntry = row.symbol;
    this.editPortfolioRow.symbol = row.symbol;
    this.editPortfolioRow.name = row.name;
    this.editPortfolioRow.currency = row.currency;
    overlay.hide();
  }

  addPosition() {
    this.selectedPortfolioRow = null;
    this.editPortfolioRow = new PortfolioRow();
    this.editPositionDialogVisible = true;
  }

  retrieve() {
    this.subs.add(this.portfolioService.retrieve().subscribe(portfolio => {
      this.portfolioRows = portfolio.positions.map(position => PortfolioRow.create(position));
    },
      error => this.notificationService.error("Portfolio", "Error")));
  }

  save() {
    let portfolio = new Portfolio();
    portfolio.positions = this.portfolioRows.map(row => row.getStockPosition());
    this.subs.add(this.portfolioService.save(portfolio).subscribe(response => {
      this.notificationService.info("Portfolio", "Saved")
    },
      error => this.notificationService.error("Portfolio", "Error")));
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
