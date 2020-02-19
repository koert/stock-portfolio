package zencode.sb.portfolio.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import yahoofinance.Stock;

/**
 * REST endpoint for stocks.
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/stocks")
public class StockEndpoint {

  @Autowired private YahooFinanceRepository yahooFinanceRepository;

  /**
   * Lever abonnement.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/latestPrice", method = RequestMethod.GET)
  public ResponseEntity<StockLatestPriceResponse> latestPrice(@PathVariable("symbol") String symbol) {
    ResponseEntity<StockLatestPriceResponse> responseEntity = null;
    Stock stock = yahooFinanceRepository.getStock(symbol);
    if (stock == null) {
      responseEntity = ResponseEntity.notFound().build();
    } else {
      StockLatestPriceResponse response = new StockLatestPriceResponse(stock.getSymbol(), stock.getQuote().getPrice());
      responseEntity = ResponseEntity.ok().body(response);
    }
    return responseEntity;
  }
}
