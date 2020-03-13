package nl.zencode.port.q.stock;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * @author Koert Zeilstra
 */
@Path("/")
@RegisterRestClient(configKey="alphaVantage")
public interface AlphaVantageClient {

  @GET
  @Path("/query")
  @Produces("application/json")
  SearchResponse searchByKeyword(@QueryParam("apikey") String apikey,
      @QueryParam("function") String function, @QueryParam("keywords") String keyword);
}

//  fun searchByKeyword(keyword: String): SearchResponse? {
//
//    val headers = HttpHeaders();
//    headers.set("Accept", "application/json");
//    val searchResponse = restTemplate.exchange(
//    "https://www.alphavantage.co/query?function=SYMBOL_SEARCH&apikey=K2K7N6LONAH5BZMB&keywords=" + keyword,
//    HttpMethod.GET, HttpEntity<HttpHeaders>(headers), SearchResponse::class.java);
//    return searchResponse.body;
//    }
//    }
//
//    data class SearchMatch(
//@JsonProperty("1. symbol") var symbol: String,
//@JsonProperty("2. name") var name: String,
//@JsonProperty("3. type") var type: String,
//@JsonProperty("4. region") var region: String,
//@JsonProperty("5. marketOpen") var marketOpen: String,
//@JsonProperty("6. marketClose") var marketClose: String,
//@JsonProperty("7. timezone") var timezone: String,
//@JsonProperty("8. currency") var currency: String,
//@JsonProperty("9. matchScore") var matchScore: Float
//    )
//
//    data class SearchResponse(var bestMatches: List<SearchMatch>)
//
