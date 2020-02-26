package zencode.sb.portfolio.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Koert Zeilstra
 */
@Component
public class AuthenticationSession {

  public Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public String getUserId() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
