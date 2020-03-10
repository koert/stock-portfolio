package nl.zencode.port.q.price;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

/**
 * @author Koert Zeilstra
 */
@RegisterForReflection
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
