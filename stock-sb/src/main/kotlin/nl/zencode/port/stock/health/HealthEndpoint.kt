package nl.zencode.port.stock.health

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * @author Koert Zeilstra
 */
@RestController
class HealthEndpoint {

  @GetMapping("/health")
  fun checkHealth() = HealthCheckResponse(true, "")
}

data class HealthCheckResponse(val ok: Boolean, val message: String)
