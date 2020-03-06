package zencode.sb.portfolio.price;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Koert Zeilstra
 */
public class PriceHistoryQuote {
//  @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ")
  public Date date;
  public BigDecimal closePrice;

  public PriceHistoryQuote() {
  }

  public PriceHistoryQuote(Calendar date, BigDecimal closePrice) {
    this.date = date.getTime();
    this.closePrice = closePrice;
  }
}
