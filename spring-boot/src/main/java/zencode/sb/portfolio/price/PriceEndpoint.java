package zencode.sb.portfolio.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import zencode.sb.portfolio.security.AuthenticationSession;

/**
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/prices")
public class PriceEndpoint {

  private static final Logger log = LoggerFactory.getLogger(PriceEndpoint.class);

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AuthenticationSession authenticationSession;

//  @Autowired
//  private RestTemplateBuilder builder;

  /**
   * Retrieve latest price of stock.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/latest", method = RequestMethod.GET)
  public StockLatestPriceResponse latestPrice(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable("symbol") String symbol) {
//    builder.defaultHeader("Authorization", authorizationHeader);
//    RestTemplate restTemplate = builder.build();
//    StockLatestPriceResponse response = restTemplate.getForObject("http://localhost:8081/stockprices/{symbol}/latest",
//        StockLatestPriceResponse.class, symbol);

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    ResponseEntity<StockLatestPriceResponse> responseEntity = restTemplate.exchange("http://localhost:8081/stockprices/{symbol}/latest",
        HttpMethod.GET,
        new HttpEntity<>(headers), StockLatestPriceResponse.class, symbol);
    return responseEntity.getBody();
//
//    String userId = (String) authenticationSession.getAuthentication().getPrincipal();
//    log.debug("getPortfolio {}", userId);
//    List<StockPosition> stockPositions = portfolioRepository.retrievePositions(userId);
//    PortfolioMessage portfolio = new PortfolioMessage();
//    portfolio.positions = stockPositions;
//    return ResponseEntity.ok().body(portfolio);
  }


}
