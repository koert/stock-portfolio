package nl.zencode.port.q.stock;

import yahoofinance.Stock;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Endpoint for stock info.
 * @author Koert Zeilstra
 */
@Path("/stocks")
public class StockEndpoint {

  @Inject AlphaVantageRepository alphaVantageRepository;
  @Inject YahooFinanceRepository yahooFinanceRepository;

  @GET
  @Path("/search")
  public List<StockSearchMatch> search(@QueryParam("keyword") String keyword) {
    List<StockSearchMatch> stockSearchResponse = null;
    if (keyword != null) {
      SearchResponse searchResponse = alphaVantageRepository.searchByKeyword(keyword);
      if (searchResponse != null && searchResponse.bestMatches.size() > 0) {
        stockSearchResponse = searchResponse.bestMatches.stream()
            .map(this::toStockMatch)
        .collect(Collectors.toList());
      } else {
        stockSearchResponse = Collections.emptyList();
      }
    } else {
      stockSearchResponse = Collections.emptyList();
    }
    return stockSearchResponse;
  }

  @GET
  @Path("/{symbol}/info")
  public StockInfo info(@PathParam("symbol") String symbol) {
    Stock stock = yahooFinanceRepository.getStock(symbol);
    if (stock == null) {
      throw new NotFoundException("Stock not found: " + symbol);
    }
    return toStockInfo(stock);
  }

  private StockSearchMatch toStockMatch(SearchMatch match) {
    return new StockSearchMatch(match.symbol, match.name, match.currency, match.matchScore);
  }

  private StockInfo toStockInfo(Stock stock) {
    return new StockInfo(stock.getSymbol(), stock.getName(), stock.getCurrency(), stock.getStockExchange());
  }

}
