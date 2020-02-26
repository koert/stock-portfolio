package zencode.sb.portfolio.port;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Koert Zeilstra
 */
public class StockPosition {
  public String symbol;
  public Integer amount;
  public BigDecimal buyPrice;
  public Date buyDate;
  public BigDecimal latestPrice;
  public Date latestDate;
}
