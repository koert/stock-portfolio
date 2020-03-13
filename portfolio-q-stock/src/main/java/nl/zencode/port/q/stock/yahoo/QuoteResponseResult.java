package nl.zencode.port.q.stock.yahoo;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;

/**
 * Yahoo finance stock response result.
 * @author Koert Zeilstra
 */
@RegisterForReflection
public class QuoteResponseResult {
  public String symbol;
  public String language;
  public String region;
  public String currency;
  public String shortName;
  public String longName;
  public String fullExchangeName;
  public BigDecimal regularMarketPrice;
}
