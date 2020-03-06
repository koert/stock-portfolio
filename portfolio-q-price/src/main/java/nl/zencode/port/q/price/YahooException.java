package nl.zencode.port.q.price;

/**
 * Yahoo communication exception.
 * @author Koert Zeilstra
 */
public class YahooException extends RuntimeException {
  /**
   * Constructor.
   * @param e Cause of exception.
   */
  public YahooException(Exception e) {
    super(e);
  }
}
