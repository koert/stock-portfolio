package nl.zencode.port.q.stock;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * @author Koert Zeilstra
 */
@RegisterForReflection
public class StockInfo {
  public String symbol;
  public String name;
  public String currency;
  public String exchange;

  public StockInfo() {
  }

  public StockInfo(String symbol, String name, String currency, String exchange) {
    this.symbol = symbol;
    this.name = name;
    this.currency = currency;
    this.exchange = exchange;
  }
}
