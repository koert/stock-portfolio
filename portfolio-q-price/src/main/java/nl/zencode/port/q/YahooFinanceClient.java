package nl.zencode.port.q;

import nl.zencode.port.q.stock.yahoo.FinQuoteResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;

/**
 * @author Koert Zeilstra
 */
@Path("/")
@RegisterRestClient(configKey="yahooFinance")
public interface YahooFinanceClient {

  @GET
  @Path("/quote")
//  @Produces("text/html")
  String getQuote(@QueryParam("symbols") String symbols);

  @GET
  @Path("/quote")
  @Produces("application/json")
  FinQuoteResponse getQuote1(@QueryParam("symbols") String symbols);

  @GET
  @Path("/download/{symbol}")
  @Produces("application/json")
  FinQuoteResponse getPriceHistory(@PathParam("symbol") String symbol,
      @QueryParam("period1") long period1,
      @QueryParam("period2") long period2,
      @QueryParam("interval") String interval /*,
      @QueryParam("crumb") String crumb,
      @HeaderParam("Cookie") String cookie */
      );

//          params.put("period1", String.valueOf(this.from.getTimeInMillis() / 1000));
//        params.put("period2", String.valueOf(this.to.getTimeInMillis() / 1000));
//
//        params.put("interval", this.interval.getTag());

}
