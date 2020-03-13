package nl.zencode.port.q.price;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class StockPricesEndpointTest {
  @Test
  public void history() {
    RestAssured.when().get("/prices/UHAL/history").then()
        .contentType("application/json");
//                .body(equalTo("hello jaxrs 1"));
  }

}
