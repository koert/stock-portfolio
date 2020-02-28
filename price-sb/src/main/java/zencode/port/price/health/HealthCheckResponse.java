package zencode.port.price.health;

/**
 * Response for health check.
 * @author Koert Zeilstra
 */
public class HealthCheckResponse {
  public boolean ok;
  public String message;

  /**
   * Constructor.
   * @param ok True if OK.
   * @param message Message text.
   */
  public HealthCheckResponse(boolean ok, String message) {
    this.ok = ok;
    this.message = message;
  }
}
