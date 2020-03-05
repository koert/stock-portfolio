package nl.zencode.port.stock.endpoint

import org.springframework.stereotype.Component
import yahoofinance.Stock
import yahoofinance.YahooFinance
import java.io.IOException

/**
 * Wrapper for communication with Yahoo Finance.
 * @author Koert Zeilstra
 */
@Component
class YahooFinanceRepository {
  /**
   * Retrieve stock information.
   * @param symbol Stock symbol.
   * @return Stock.
   */
  fun getStock(symbol: String?): Stock {
    return try {
      YahooFinance.get(symbol)
    } catch (e: IOException) {
      throw YahooException(e)
    }
  }
}