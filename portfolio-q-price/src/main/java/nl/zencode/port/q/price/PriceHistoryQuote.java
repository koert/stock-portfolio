package nl.zencode.port.q.price;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Koert Zeilstra
 */
@RegisterForReflection
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
