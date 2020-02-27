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

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public BigDecimal getBuyPrice() {
    return buyPrice;
  }

  public void setBuyPrice(BigDecimal buyPrice) {
    this.buyPrice = buyPrice;
  }

  public Date getBuyDate() {
    return buyDate;
  }

  public void setBuyDate(Date buyDate) {
    this.buyDate = buyDate;
  }

  public BigDecimal getLatestPrice() {
    return latestPrice;
  }

  public void setLatestPrice(BigDecimal latestPrice) {
    this.latestPrice = latestPrice;
  }

  public Date getLatestDate() {
    return latestDate;
  }

  public void setLatestDate(Date latestDate) {
    this.latestDate = latestDate;
  }
}
