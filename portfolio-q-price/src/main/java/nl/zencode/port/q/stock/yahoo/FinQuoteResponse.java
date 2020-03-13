package nl.zencode.port.q.stock.yahoo;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Yahoo finance stock response.
 * @author Koert Zeilstra
 */
@RegisterForReflection
public class FinQuoteResponse {
  public QuoteResponse quoteResponse;
}
