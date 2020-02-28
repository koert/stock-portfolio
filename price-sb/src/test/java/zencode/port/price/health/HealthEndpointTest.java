package zencode.port.price.health;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Unit test for HealthEndpoint.
 * @author Koert Zeilstra
 */
@ExtendWith(MockitoExtension.class)
class HealthEndpointTest {

  @InjectMocks HealthEndpoint endpoint;

  @Test
  @DisplayName("check")
  void check() {

    ResponseEntity<HealthCheckResponse> responseEntity = endpoint.check();

    Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }
}