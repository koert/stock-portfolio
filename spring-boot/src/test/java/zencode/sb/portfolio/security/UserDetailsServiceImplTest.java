package zencode.sb.portfolio.security;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Koert Zeilstra
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImplTest.class);

  @Test
  void testPassword() {
    String encoded = new BCryptPasswordEncoder().encode("123");
    log.debug("encoded: {}", encoded);
  }
}