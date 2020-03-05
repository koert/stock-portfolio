package zencode.sb.portfolio.stock;

import java.util.List;

/**
 * @author Koert Zeilstra
 */
public class StockSearchResponse {
  public List<StockMatch> matches;

  public StockSearchResponse() {
  }

  public StockSearchResponse(List<StockMatch> matches) {
    this.matches = matches;
  }

}
