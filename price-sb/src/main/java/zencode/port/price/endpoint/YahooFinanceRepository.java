package zencode.port.price.endpoint;

import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * Wrapper for communication with Yahoo Finance.
 * @author Koert Zeilstra
 */
@Component
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
