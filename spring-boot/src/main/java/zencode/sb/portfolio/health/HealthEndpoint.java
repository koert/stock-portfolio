package zencode.sb.portfolio.health;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST endpoint for health check.
 * @author Koert Zeilstra
 */
@RestController
@RequestMapping("/health")
public class HealthEndpoint {

  /**
   * Lever abonnement.
   * @return Service response with status.
   */
  @RequestMapping(value="/check", method = RequestMethod.GET)
  public ResponseEntity<HealthCheckResponse> check() {
    return ResponseEntity.ok().body(new HealthCheckResponse(true, ""));
  }
}
