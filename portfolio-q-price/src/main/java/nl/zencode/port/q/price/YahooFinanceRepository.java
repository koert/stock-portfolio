package nl.zencode.port.q.price;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;

/**
 * Wrapper for communication with Yahoo Finance.
 * @author Koert Zeilstra
 */
@ApplicationScoped
public class YahooFinanceRepository {

  /**
   * Retrieve stock information.
   * @param symbol Stock symbol.
   * @return Stock.
   */
  public Stock getStock(String symbol) {
    try {
      return YahooFinance.get(symbol);
    } catch (IOException e) {
      throw new YahooException(e);
    }
  }
}
