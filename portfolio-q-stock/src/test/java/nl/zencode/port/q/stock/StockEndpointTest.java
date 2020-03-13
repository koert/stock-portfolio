package nl.zencode.port.q.stock;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class StockEndpointTest
{
//    @Test
//    public void testInfo1() {
//        RestAssured.when().get("/stocks/UHAL/info1").then()
//            .contentType("application/json");
////                .body(equalTo("hello jaxrs 1"));
//    }

    @Test
    public void info() {
        RestAssured.when().get("/stocks/UHAL/info").then()
                .contentType("application/json");
//                .body(equalTo("hello jaxrs 2"));
    }

}
