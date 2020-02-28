package nl.zencode.port.stock.endpoint

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

/**
 *
 * @author Koert Zeilstra
 */
@Component
class AlphaVantageRepository(
   @Autowired private val restTemplate: RestTemplate) {

  fun searchByIsin(isin: String): SearchResponse? {

    val headers = HttpHeaders();
    headers.set("Accept", "application/json");
    val searchResponse = restTemplate.exchange(
       "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&apikey=K2K7N6LONAH5BZMB&keywords=" + isin,
       HttpMethod.GET, HttpEntity<HttpHeaders>(headers), SearchResponse::class.java);
    return searchResponse.body;
  }
}

data class SearchMatch(
   @JsonProperty("1. symbol") var symbol: String,
   @JsonProperty("2. name") var name: String,
   @JsonProperty("3. type") var type: String,
   @JsonProperty("4. region") var region: String,
   @JsonProperty("5. marketOpen") var marketOpen: String,
   @JsonProperty("6. marketClose") var marketClose: String,
   @JsonProperty("7. timezone") var timezone: String,
   @JsonProperty("8. currency") var currency: String,
   @JsonProperty("9. matchScore") var matchScore: Float
)

data class SearchResponse(var bestMatches: List<SearchMatch>)

