package nl.zencode.port.q;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.zencode.port.q.stock.StockInfo;
import nl.zencode.port.q.stock.YahooException;
import nl.zencode.port.q.stock.yahoo.FinQuoteResponse;
import nl.zencode.port.q.stock.yahoo.QuoteResponseResult;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.Interval;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;

/**
 * Wrapper for communication with Yahoo Finance.
 * @author Koert Zeilstra
 */
@ApplicationScoped
public class YahooFinanceRepository {

  @Inject
  @RestClient
  YahooFinanceClient yahooFinanceClient;

  /**
   * Retrieve stock information.
   * @param symbol Stock symbol.
   * @return Stock.
   */
  public Stock getStock(String symbol) {
    try {
      return YahooFinance.get(symbol);
    } catch (IOException e) {
      throw new YahooException(e);
    }
  }

  /**
   * Retrieve stock information.
   * @param symbol Stock symbol.
   * @return Stock.
   */
  public StockInfo getStock1(String symbol) {
    try {
    StockInfo stockInfo = null;
    FinQuoteResponse finQuoteResponse = yahooFinanceClient.getQuote1(symbol);
    if (finQuoteResponse != null && finQuoteResponse.quoteResponse != null && finQuoteResponse.quoteResponse.result != null && finQuoteResponse.quoteResponse.result.size() > 0) {
      QuoteResponseResult quoteResponseResult = finQuoteResponse.quoteResponse.result.get(0);
      stockInfo = new StockInfo();
      stockInfo.symbol = quoteResponseResult.symbol;
      stockInfo.name = quoteResponseResult.longName;
      stockInfo.currency = quoteResponseResult.currency;
      stockInfo.exchange = quoteResponseResult.fullExchangeName;
    } else {
      if (finQuoteResponse.quoteResponse != null && finQuoteResponse.quoteResponse.error != null) {
        throw new RuntimeException(finQuoteResponse.quoteResponse.error);
      }
    }
    return stockInfo;
    } catch (RuntimeException e) {
      e.printStackTrace();
      throw new YahooException(e);
    }
  }

  /**
   * Retrieve stock information.
   * @param symbol Stock symbol.
   * @return Stock.
   */
  public StockInfo getStock2(String symbol) {
//    try {
    StockInfo stockInfo = null;
    String quoteText = yahooFinanceClient.getQuote(symbol);
    if (quoteText != null && quoteText.length() > 0) {
      try {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(quoteText);
        if (node.has("quoteResponse") && node.get("quoteResponse").has("result")) {
          JsonNode resultNode = node.get("quoteResponse").get("result");
          if (resultNode.size() > 0) {
            JsonNode resultNode0 = resultNode.get(0);
            stockInfo = new StockInfo();
            stockInfo.symbol = resultNode0.get("symbol").asText();
            if(resultNode0.has("longName")) {
              stockInfo.name = resultNode0.get("longName").asText();
            } else {
              stockInfo.name = resultNode0.get("shortName").asText();
            }
            stockInfo.currency = resultNode0.get("currency").asText();
            stockInfo.exchange = resultNode0.get("fullExchangeName").asText();
            stockInfo.latestPrice = new BigDecimal(resultNode0.get("regularMarketPrice").asText());
          }
        } else {
          throw new RuntimeException("Invalid response");
        }
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return stockInfo;
//    } catch (IOException e) {
//      throw new YahooException(e);
//    }
  }

  public FinQuoteResponse getPriceHistory(String symbol, Date start, Date end, Interval interval, String crumb, String cookie) {
    FinQuoteResponse priceHistory = null;
    try {
      priceHistory = yahooFinanceClient.getPriceHistory(URLEncoder.encode(symbol, "UTF-8"),
          start.getTime() / 1000, end.getTime() / 1000,
          interval.getTag()/*, crumb, cookie*/);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return priceHistory;
  }
}
