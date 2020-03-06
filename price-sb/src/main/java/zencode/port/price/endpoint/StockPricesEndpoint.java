package zencode.port.price.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST endpoint for stocks.
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/stockprices")
public class StockPricesEndpoint {

  @Autowired private YahooFinanceRepository yahooFinanceRepository;

  /**
   * Retrieve latest price of stock.
   * @return Service response with status.
   */
  @RequestMapping(value="/{symbol}/latest", method = RequestMethod.GET)
  public ResponseEntity<StockLatestPriceResponse> latestPrice(@PathVariable("symbol") String symbol) {
    ResponseEntity<StockLatestPriceResponse> responseEntity = null;
    Stock stock = yahooFinanceRepository.getStock(symbol);
    if (stock == null) {
      responseEntity = ResponseEntity.notFound().build();
    } else {
      StockLatestPriceResponse response = new StockLatestPriceResponse(stock.getSymbol(), stock.getQuote().getPrice());
      responseEntity = ResponseEntity.ok().body(response);
    }
    return responseEntity;
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
  @RequestMapping(value="/{symbol}/history", method = RequestMethod.GET)
  public ResponseEntity<StockPriceHistoryResponse> stock(@PathVariable("symbol") String symbol,
      @RequestParam("startDate") String startDateParam,
      @RequestParam("endDate") String endDateParam,
      @RequestParam("interval") String intervalParam) throws IOException {
    Stock stock = YahooFinance.get(symbol);

    ResponseEntity<StockPriceHistoryResponse> response = null;
    if (stock == null) {
      response = ResponseEntity.notFound().build();
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
      StockPriceHistoryResponse stockHistoryResponse = new StockPriceHistoryResponse(stock.getSymbol(), priceHistoryQuotes);
      response = ResponseEntity.ok().body(stockHistoryResponse);
    }
    return response;
  }

}
