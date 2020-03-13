package nl.zencode.port.security;

import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Unit test ApplicationUserRepository.
 * @author Koert Zeilstra
 */
@ExtendWith(MockitoExtension.class)
class ApplicationUserRepositoryTest {

  @InjectMocks ApplicationUserRepository repository;

  @Nested
  @DisplayName("isAuthenticated()")
  class IsAuthenticated {

    @BeforeEach
    void initialize() {
      repository.initialize();
    }

    @Test
    @DisplayName("with unknown user")
    void unknownUser() {
      boolean authenticated = repository.isAuthenticated("guest-abc", "invalid-password");

      Assertions.assertThat(authenticated).isFalse();
    }

    @Test
    @DisplayName("with invalid password")
    void invalidPassword() {
      boolean authenticated = repository.isAuthenticated("guest", "invalid-password");

      Assertions.assertThat(authenticated).isFalse();
    }

    @Test
    @DisplayName("with valid password")
    void validPassword() {
      String encodedPassword = DigestUtils.sha256Hex("welcome123");
      System.out.println("encoded: " + encodedPassword);

      boolean authenticated = repository.isAuthenticated("guest", "welcome123");

      Assertions.assertThat(authenticated).isTrue();
    }
  }

}