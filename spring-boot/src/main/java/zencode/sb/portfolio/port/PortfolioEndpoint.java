package zencode.sb.portfolio.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zencode.sb.portfolio.security.AuthenticationSession;

import java.util.List;

/**
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/portfolio")
public class PortfolioEndpoint {

  private static final Logger log = LoggerFactory.getLogger(PortfolioEndpoint.class);

  @Autowired
  private PortfolioRepository portfolioRepository;
  @Autowired
  private AuthenticationSession authenticationSession;

  /**
   * Retrieve portfolio from database.
   * @return Response.
   */
  @RequestMapping(value="/positions", method = RequestMethod.GET)
  public ResponseEntity<PortfolioMessage> getPortfolio() {
    String userId = (String) authenticationSession.getAuthentication().getPrincipal();
    log.debug("getPortfolio {}", userId);
    List<StockPosition> stockPositions = portfolioRepository.retrievePositions(userId);
    PortfolioMessage portfolio = new PortfolioMessage();
    portfolio.positions = stockPositions;
    return ResponseEntity.ok().body(portfolio);
  }

  /**
   * Save portfolio in database.
   * @param portfolio Portfolio with positions.
   * @return Response.
   */
  @RequestMapping(value="/positions", method = RequestMethod.PUT)
  public ResponseEntity<Void> savePortfolio(@RequestBody PortfolioMessage portfolio) {
    log.debug("savePortfolio " + portfolio.toString());
    if (portfolio.positions != null) {
      String userId = (String) authenticationSession.getAuthentication().getPrincipal();
      portfolioRepository.savePositions(userId, portfolio.positions);
    }
    return ResponseEntity.ok().build();
  }

}
