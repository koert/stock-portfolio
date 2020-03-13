package nl.zencode.port.q.stock.yahoo;

import io.quarkus.runtime.annotations.RegisterForReflection;

import java.util.List;

/**
 * Yahoo finance stock response.
 * @author Koert Zeilstra
 */
@RegisterForReflection
public class QuoteResponse {
  public List<QuoteResponseResult> result;
  public String error;
}
