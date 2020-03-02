package zencode.sb.portfolio.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import yahoofinance.Stock;

/**
 * REST endpoint for stocks.
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/stocks")
public class StockEndpoint {

  @Autowired private RestTemplate restTemplate;

  @Autowired private YahooFinanceRepository yahooFinanceRepository;

  /**
   * Search stock.
   * @return Service response with status.
   */
  @RequestMapping(value="/search", method = RequestMethod.GET)
  public zencode.sb.portfolio.price.StockLatestPriceResponse search(@RequestHeader("Authorization") String authorizationHeader,
      @RequestParam("keyword") String keyword) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    ResponseEntity<zencode.sb.portfolio.price.StockLatestPriceResponse> responseEntity = restTemplate.exchange("http://localhost:8082/stocks/search?keyword=" + keyword,
        HttpMethod.GET,
        new HttpEntity<>(headers), zencode.sb.portfolio.price.StockLatestPriceResponse.class);
    return responseEntity.getBody();
  }

  /**
   * Get general stock info.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/info", method = RequestMethod.GET)
  public zencode.sb.portfolio.price.StockLatestPriceResponse info(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable("symbol") String symbol) {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    ResponseEntity<zencode.sb.portfolio.price.StockLatestPriceResponse> responseEntity = restTemplate.exchange("http://localhost:8082/stocks/{symbol}/info",
        HttpMethod.GET,
        new HttpEntity<>(headers), zencode.sb.portfolio.price.StockLatestPriceResponse.class, symbol);
    return responseEntity.getBody();
  }

  /**
   * Get latest stock price.
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
