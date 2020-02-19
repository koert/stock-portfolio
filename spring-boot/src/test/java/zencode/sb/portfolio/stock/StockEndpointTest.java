package zencode.sb.portfolio.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import yahoofinance.Stock;
import yahoofinance.quotes.stock.StockQuote;

import java.math.BigDecimal;

/**
 * @author Koert Zeilstra
 */
@ExtendWith(MockitoExtension.class)
class StockEndpointTest {
  @InjectMocks StockEndpoint endpoint;

  @Mock YahooFinanceRepository yahooFinanceRepository;
  @Mock Stock stock;
  @Mock StockQuote stockQuote;

  @DisplayName("latestPrice")
  @Nested class LatestPrice {
    @Test
    @DisplayName("with not found")
    void notFound() {
      ResponseEntity<StockLatestPriceResponse> responseEntity = endpoint.latestPrice("ABC");

      Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("symbol found")
    void symbolFound() {
      Mockito.when(yahooFinanceRepository.getStock("ABC")).thenReturn(stock);
      Mockito.when(stock.getSymbol()).thenReturn("ABC2");
      Mockito.when(stock.getQuote()).thenReturn(stockQuote);
      Mockito.when(stockQuote.getPrice()).thenReturn(BigDecimal.TEN);

      ResponseEntity<StockLatestPriceResponse> responseEntity = endpoint.latestPrice("ABC");

      Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      StockLatestPriceResponse priceResponse = responseEntity.getBody();
      Assertions.assertThat(priceResponse.symbol).isEqualTo("ABC2");
      Assertions.assertThat(priceResponse.latestPrice).isEqualTo(BigDecimal.TEN);
    }

  }
}