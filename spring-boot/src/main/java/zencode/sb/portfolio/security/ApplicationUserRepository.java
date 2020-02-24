package zencode.sb.portfolio.security;

import org.springframework.stereotype.Repository;

/**
 * @author Koert Zeilstra
 */
@Repository
public class ApplicationUserRepository {

  public ApplicationUser findByUsername(String userName) {
    ApplicationUser applicationUser = new ApplicationUser();
    applicationUser.setUserName(userName);
    applicationUser.setPassword("$2a$10$MaV48UBszH2eRb/htF5Lq.IN3v8acVxTG55vYW8A3ZH.nEatMukgW");
    return applicationUser;
  }
}
