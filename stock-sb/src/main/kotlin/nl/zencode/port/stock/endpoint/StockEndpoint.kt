package nl.zencode.port.stock.endpoint

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * REST endpoint for stock info.
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/stocks")
class StockEndpoint(@Autowired private val alphaVantageRepository: AlphaVantageRepository) {

  @GetMapping("/search")
  fun search(@RequestParam("isin") isin: String?): StockInfo? {
    return if (isin != null) {
      val searchResponse = alphaVantageRepository.searchByIsin(isin)
      if (searchResponse != null && searchResponse.bestMatches.size > 0) {
        val match = searchResponse.bestMatches[0]
        StockInfo(isin, match.symbol, match.name, match.type, match.currency, match.region, match.marketOpen, match.marketClose, match.timezone)
      } else {
        null
      }
    } else {
      null
    }
  }
}

data class StockInfo(val isin: String, val symbol: String, val name: String, val type: String, val currency: String,
                     val region: String?, val marketOpen: String?, val marketClose: String?, val timezone: String?)
