package zencode.sb.portfolio.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import zencode.sb.portfolio.security.AuthenticationSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/prices")
public class PriceEndpoint {

  private static final Logger log = LoggerFactory.getLogger(PriceEndpoint.class);

  @Value("${client.prices.url}") private String clientPricesUrl;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private AuthenticationSession authenticationSession;

  /**
   * Retrieve latest price of stock.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/latest", method = RequestMethod.GET)
  public StockLatestPriceResponse latestPrice(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable("symbol") String symbol) {

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    ResponseEntity<StockLatestPriceResponse> responseEntity = restTemplate.exchange(clientPricesUrl + "/stockprices/{symbol}/latest",
        HttpMethod.GET,
        new HttpEntity<>(headers), StockLatestPriceResponse.class, symbol);
    return responseEntity.getBody();
  }

  /**
   * Retrieve latest price of stock.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/history", method = RequestMethod.GET)
  public StockPriceHistoryResponse history(@RequestHeader("Authorization") String authorizationHeader,
      @PathVariable("symbol") String symbol,
      @RequestParam("startDate") String startDateParam,
      @RequestParam("endDate") String endDateParam,
      @RequestParam("interval") String intervalParam) {

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", authorizationHeader);
    List<String> queryParameters = new ArrayList<>();
    if (startDateParam != null) {
      queryParameters.add("startDate=" + startDateParam);
    }
    if (endDateParam != null) {
      queryParameters.add("endDate=" + endDateParam);
    }
    if (intervalParam != null) {
      queryParameters.add("interval=" + intervalParam);
    }
    String queryString = "";
    if (queryParameters.size() > 0) {
      queryString = "?" + String.join("&", queryParameters);
    }
    ResponseEntity<StockPriceHistoryResponse> responseEntity = restTemplate.exchange(
        clientPricesUrl + "/stockprices/{symbol}/history" + queryString,
        HttpMethod.GET,
        new HttpEntity<>(headers), StockPriceHistoryResponse.class, symbol);
    return responseEntity.getBody();
  }


}
