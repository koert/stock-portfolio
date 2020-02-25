package zencode.sb.portfolio.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    log.debug("Welkom123: {}", encoder.encode("Welkom123!"));
    log.debug("Gk3ec9rAwCRMY5cM: {}", encoder.encode("Gk3ec9rAwCRMY5cM"));
  }
}