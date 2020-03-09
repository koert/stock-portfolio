package nl.zencode.port.q.stock;

/**
 * @author Koert Zeilstra
 */
public class StockSearchMatch {
  public String symbol;
  public String name;
  public String currency;
  public Float matchScore;

  public StockSearchMatch() {
  }

  public StockSearchMatch(String symbol, String name, String currency, Float matchScore) {
    this.symbol = symbol;
    this.name = name;
    this.currency = currency;
    this.matchScore = matchScore;
  }
}
