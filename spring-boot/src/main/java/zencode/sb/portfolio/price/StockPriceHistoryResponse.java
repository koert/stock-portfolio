package zencode.sb.portfolio.price;

import java.util.List;

/**
 * @author Koert Zeilstra
 */
public class StockPriceHistoryResponse {
  public String symbol;
  public List<PriceHistoryQuote> quotes;

  public StockPriceHistoryResponse() {
  }

  public StockPriceHistoryResponse(String symbol, List<PriceHistoryQuote> quotes) {
    this.symbol = symbol;
    this.quotes = quotes;
  }

}
