package zencode.sb.portfolio.stock;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.io.IOException;

/**
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/stocks")
public class StockEndpoint {

  /**
   * Lever abonnement.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/latestPrice", method = RequestMethod.GET)
  public ResponseEntity<StockLatestPriceResponse> latestPrice(@PathVariable("symbol") String symbol) {
    ResponseEntity<StockLatestPriceResponse> responseEntity = null;
    try {
      Stock stock = YahooFinance.get(symbol);
      StockLatestPriceResponse response = new StockLatestPriceResponse(stock.getSymbol(), stock.getQuote().getPrice());
      responseEntity = ResponseEntity.ok().body(response);
    } catch (IOException e) {
      responseEntity = ResponseEntity.notFound().build();
    }

    return responseEntity;
  }
}
