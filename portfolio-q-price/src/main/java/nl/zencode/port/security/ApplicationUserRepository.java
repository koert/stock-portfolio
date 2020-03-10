package nl.zencode.port.security;

import org.apache.commons.codec.digest.DigestUtils;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Koert Zeilstra
 */
@ApplicationScoped
public class ApplicationUserRepository {
  private Map<String, String> passwords = new HashMap<>();

  @PostConstruct
  public void initialize() {
    this.passwords.put("guest", "a68349561396ec264a350847024a4521d00beaa3358660c2709a80f31c7acdd0");
    this.passwords.put("kzeilstra", "c7052310ddf185a6949e650177781a8ca242144a840b8956b3211a5a03bb8a8b");
  }

  public boolean isAuthenticated(String userId, String password) {
    boolean authenticated = false;
    String passwordHash = DigestUtils.sha256Hex(password);
    if (this.passwords.containsKey(userId)) {
      authenticated = passwordHash.equals(this.passwords.get(userId));
    }
    return authenticated;
  }

}
