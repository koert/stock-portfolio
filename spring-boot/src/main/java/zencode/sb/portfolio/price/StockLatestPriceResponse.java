package zencode.sb.portfolio.price;

import java.math.BigDecimal;

/**
 * @author Koert Zeilstra
 */
public class StockLatestPriceResponse {
  public String symbol;
  public BigDecimal latestPrice;

  public StockLatestPriceResponse() {
  }

  public StockLatestPriceResponse(String symbol, BigDecimal latestPrice) {
    this.symbol = symbol;
    this.latestPrice = latestPrice;
  }

}
