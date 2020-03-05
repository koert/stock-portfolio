package nl.zencode.port.stock.endpoint

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import yahoofinance.Stock

/**
 * REST endpoint for stock search and info.
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/stocks")
class StockEndpoint(@Autowired private val alphaVantageRepository: AlphaVantageRepository,
                    @Autowired private val yahooFinanceRepository: YahooFinanceRepository) {

  @GetMapping("/search")
  fun search(@RequestParam("keyword") keyword: String?): StockSearchResponse {
    val stockSearchResponse: StockSearchResponse = if (keyword != null) {
      val searchResponse = alphaVantageRepository.searchByKeyword(keyword)
      if (searchResponse != null && searchResponse.bestMatches.size > 0) {
        StockSearchResponse(searchResponse.bestMatches.map{ match -> toStockMatch(match) })
      } else {
        StockSearchResponse(emptyList())
      }
    } else {
      StockSearchResponse(emptyList())
    }
    return stockSearchResponse;
  }

  @GetMapping("/{symbol}/info")
  fun info(@PathVariable("symbol") symbol: String): StockInfo? {
    val stock: Stock? = yahooFinanceRepository.getStock(symbol);
    return if (stock == null) {
      return null;
    } else {
      toStockInfo(stock);
    }
  }

  private fun toStockMatch(match: SearchMatch): StockMatch = StockMatch(match.symbol, match.name, match.currency)

  private fun toStockInfo(stock: Stock): StockInfo  = StockInfo(stock.symbol, stock.name, stock.currency, stock.stockExchange)
}

data class StockMatch(val symbol: String, val name: String, val currency: String)

data class StockInfo(val symbol: String, val name: String, val currency: String, val exchange: String?)

data class StockSearchResponse(val matches: List<StockMatch>)

