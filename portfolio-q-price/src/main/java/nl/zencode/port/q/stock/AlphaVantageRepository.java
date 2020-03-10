package nl.zencode.port.q.stock;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Koert Zeilstra
 */
@ApplicationScoped
public class AlphaVantageRepository {

  @Inject
  @RestClient
  AlphaVantageClient alphaVantageClient;

  SearchResponse searchByKeyword(String keyword) {
    return alphaVantageClient.searchByKeyword("K2K7N6LONAH5BZMB", "SYMBOL_SEARCH", keyword);
  }

}
