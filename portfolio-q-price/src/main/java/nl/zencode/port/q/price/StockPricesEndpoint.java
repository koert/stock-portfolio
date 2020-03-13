package nl.zencode.port.q.price;

import nl.zencode.port.q.YahooFinanceRepository;
import nl.zencode.port.q.stock.StockInfo;
import nl.zencode.port.q.stock.yahoo.FinQuoteResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoint for retrieval of stock prices.
 * @author Koert Zeilstra
 */
@Path("/prices")
@Produces(MediaType.APPLICATION_JSON)
public class StockPricesEndpoint {

  private static final Logger log = LoggerFactory.getLogger(StockPricesEndpoint.class);

  @Inject YahooFinanceRepository yahooFinanceRepository;

  @GET
  @Path("/{symbol}/latest")
  public StockLatestPriceResponse latest(@PathParam("symbol") String symbol) {
    StockLatestPriceResponse response = null;
    Stock stock = null;
    try {
      stock = YahooFinance.get(symbol);
      if (stock == null) {
        throw new NotFoundException("Stock not found: " + symbol);
      } else {
        response = new StockLatestPriceResponse(stock.getSymbol(), stock.getQuote().getPrice());
      }
    } catch (IOException e) {
      throw new RuntimeException("IOException", e);
    }
    return response;
  }

  @GET
  @Path("/{symbol}/latestPrice")
  public StockLatestPriceResponse latestPrice(@PathParam("symbol") String symbol) {
    StockLatestPriceResponse response = null;
    StockInfo stock = yahooFinanceRepository.getStock1(symbol);
    if (stock == null) {
      throw new NotFoundException("Stock not found: " + symbol);
    } else {
      response = new StockLatestPriceResponse(stock.symbol, stock.latestPrice);
    }
    return response;
  }

  /**
   * Retrieve price history.
   * @param symbol Stock symbol.
   * @param startDateParam Start date.
   * @param endDateParam End date.
   * @param intervalParam Interval - can be DAILY, WEEKLY, MONTHLY
   * @return Response with StockPriceHistoryResponse
   * @throws IOException Failed to retrieve price history.
   */
  @GET
  @Path("/{symbol}/history0")
  public StockPriceHistoryResponse history0(@PathParam("symbol") String symbol,
      @QueryParam("startDate") String startDateParam,
      @QueryParam("endDate") String endDateParam,
      @QueryParam("interval") String intervalParam) throws IOException {
    log.debug("history0 symbol {}", symbol);
    log.debug("history0 query {} {} {}", startDateParam, endDateParam, intervalParam);
    StockPriceHistoryResponse response = null;
    try {
      Stock stock = YahooFinance.get(symbol);
      if (stock == null) {
        throw new NotFoundException("Stock not found: " + symbol);
      } else {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try {
          if (startDateParam != null) {
            startDate.setTime(dateFormat.parse(startDateParam));
          }
          if (endDateParam != null) {
            endDate.setTime(dateFormat.parse(endDateParam));
          }
        } catch (ParseException e) {
        }
        Interval interval = Interval.MONTHLY;
        if (intervalParam != null) {
          if (intervalParam.equals("DAILY")) {
            interval = Interval.DAILY;
          }
          if (intervalParam.equals("WEEKLY")) {
            interval = Interval.WEEKLY;
          }
          if (intervalParam.equals("MONTHLY")) {
            interval = Interval.MONTHLY;
          }
        }
        log.debug("history0 stock {}", stock.toString());
        List<HistoricalQuote> history = stock.getHistory(startDate, endDate, interval);
        log.debug("history0 history {}", history.toString());
        List<PriceHistoryQuote> priceHistoryQuotes = history.stream()
            .map(quote -> new PriceHistoryQuote(quote.getDate(), quote.getClose()))
            .collect(Collectors.toList());
        log.debug("history0 priceHistoryQuotes {}", priceHistoryQuotes.toString());
        response = new StockPriceHistoryResponse(stock.getSymbol(), priceHistoryQuotes);
      }
    } catch (RuntimeException e) {
      log.error("history0 error", e);
      throw e;
    }
    return response;
  }

  /**
   * Retrieve price history.
   * @param symbol Stock symbol.
   * @param startDateParam Start date.
   * @param endDateParam End date.
   * @param intervalParam Interval - can be DAILY, WEEKLY, MONTHLY
   * @return Response with StockPriceHistoryResponse
   * @throws IOException Failed to retrieve price history.
   */
  @GET
  @Path("/{symbol}/history")
  public StockPriceHistoryResponse history(@PathParam("symbol") String symbol,
      @QueryParam("startDate") String startDateParam,
      @QueryParam("endDate") String endDateParam,
      @QueryParam("interval") String intervalParam) throws IOException {
    StockPriceHistoryResponse response = null;
    try {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        try {
          if (startDateParam != null) {
            startDate.setTime(dateFormat.parse(startDateParam));
          }
          if (endDateParam != null) {
            endDate.setTime(dateFormat.parse(endDateParam));
          }
        } catch (ParseException e) {
        }
        Interval interval = Interval.MONTHLY;
        if (intervalParam != null) {
          if (intervalParam.equals("DAILY")) {
            interval = Interval.DAILY;
          }
          if (intervalParam.equals("WEEKLY")) {
            interval = Interval.WEEKLY;
          }
          if (intervalParam.equals("MONTHLY")) {
            interval = Interval.MONTHLY;
          }
        }
      Stock stock = YahooFinance.get(symbol, startDate, endDate, interval);
        List<HistoricalQuote> history = stock.getHistory();
        List<PriceHistoryQuote> priceHistoryQuotes = history.stream()
            .map(quote -> new PriceHistoryQuote(quote.getDate(), quote.getClose()))
            .collect(Collectors.toList());
        response = new StockPriceHistoryResponse(stock.getSymbol(), priceHistoryQuotes);
    } catch (RuntimeException e) {
      log.error("history error", e);
      throw e;
    }
    return response;
  }

  /**
   * Retrieve price history.
   * @param symbol Stock symbol.
   * @param startDateParam Start date.
   * @param endDateParam End date.
   * @param intervalParam Interval - can be DAILY, WEEKLY, MONTHLY
   * @return Response with StockPriceHistoryResponse
   * @throws IOException Failed to retrieve price history.
   */
  @GET
  @Path("/{symbol}/history1")
  public StockPriceHistoryResponse history1(@PathParam("symbol") String symbol,
      @QueryParam("startDate") String startDateParam,
      @QueryParam("endDate") String endDateParam,
      @QueryParam("interval") String intervalParam) throws IOException {
    log.debug("history1 symbol {}", symbol);
    log.debug("history1 query {} {} {}", startDateParam, endDateParam, intervalParam);
//    Stock stock = YahooFinance.get(symbol);
    StockInfo stockInfo = yahooFinanceRepository.getStock1(symbol);

    StockPriceHistoryResponse response = null;
    if (stockInfo == null) {
      throw new NotFoundException("Stock not found: " + symbol);
    } else {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Calendar startDate = Calendar.getInstance();
      Calendar endDate = Calendar.getInstance();
      try {
        if (startDateParam != null) {
          startDate.setTime(dateFormat.parse(startDateParam));
        }
        if (endDateParam != null) {
          endDate.setTime(dateFormat.parse(endDateParam));
        }
      } catch (ParseException e) {
      }
      Interval interval = Interval.MONTHLY;
      if (intervalParam != null) {
        if (intervalParam.equals("DAILY")) {
          interval = Interval.DAILY;
        }
        if (intervalParam.equals("WEEKLY")) {
          interval = Interval.WEEKLY;
        }
        if (intervalParam.equals("MONTHLY")) {
          interval = Interval.MONTHLY;
        }
      }
      FinQuoteResponse priceHistory = yahooFinanceRepository.getPriceHistory(symbol, startDate.getTime(), endDate.getTime(), interval,
          "", "");

//      List<HistoricalQuote> history = stock.getHistory(startDate, endDate, interval);
//      List<PriceHistoryQuote> priceHistoryQuotes = history.stream()
//          .map(quote -> new PriceHistoryQuote(quote.getDate(), quote.getClose()))
//          .collect(Collectors.toList());
      List<PriceHistoryQuote> priceHistoryQuotes = new ArrayList<>();
      response = new StockPriceHistoryResponse(stockInfo.symbol, priceHistoryQuotes);
    }
    return response;
  }

}
