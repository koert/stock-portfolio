package nl.zencode.port.q.price;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Koert Zeilstra
 */
@Path("/prices")
public class StockPricesEndpoint {

  @Inject YahooFinanceRepository yahooFinanceRepository;

  @GET
  @Path("/{symbol}/latest")
  public StockLatestPriceResponse latestPrice(@PathParam("symbol") String symbol) {
    StockLatestPriceResponse response = null;
    Stock stock = yahooFinanceRepository.getStock(symbol);
    if (stock == null) {
      throw new NotFoundException("Stock not found: " + symbol);
    } else {
      response = new StockLatestPriceResponse(stock.getSymbol(), stock.getQuote().getPrice());
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
    Stock stock = YahooFinance.get(symbol);

    StockPriceHistoryResponse response = null;
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
      List<HistoricalQuote> history = stock.getHistory(startDate, endDate, interval);
      List<PriceHistoryQuote> priceHistoryQuotes = history.stream()
          .map(quote -> new PriceHistoryQuote(quote.getDate(), quote.getClose()))
          .collect(Collectors.toList());
      response = new StockPriceHistoryResponse(stock.getSymbol(), priceHistoryQuotes);
    }
    return response;
  }

}
