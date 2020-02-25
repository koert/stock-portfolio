package zencode.sb.portfolio.security;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Koert Zeilstra
 */
@Repository
public class ApplicationUserRepository {

  private Map<String, String> passwords = new HashMap<>();

  @PostConstruct
  public void initialize() {
    this.passwords.put("guest", "$2a$10$sekw7hnxOLRfOvJYBnt12uc3GhQl9Rk.NKqPjLpbyYy1gfcUyCm0i");
    this.passwords.put("koert", "$2a$10$bTJngWsw6WIwqxjZXj/j7eUBppFfFyt1STE3Q7gO6mO0YS.vbfpsq");
  }

  public ApplicationUser findByUsername(String userName) {
    ApplicationUser applicationUser = null;
    if (this.passwords.containsKey(userName)) {
      applicationUser = new ApplicationUser();
      applicationUser.setUserName(userName);
      applicationUser.setPassword(this.passwords.get(userName));
    }
    return applicationUser;
  }
}
