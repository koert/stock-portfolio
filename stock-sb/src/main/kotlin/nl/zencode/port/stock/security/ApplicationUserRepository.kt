package nl.zencode.port.stock.security

import org.springframework.stereotype.Repository
import java.util.*
import javax.annotation.PostConstruct

/**
 * @author Koert Zeilstra
 */
@Repository
class ApplicationUserRepository {
  private val passwords: MutableMap<String, String?> = HashMap()
  @PostConstruct
  fun initialize() {
    passwords["guest"] = "$2a$10\$sekw7hnxOLRfOvJYBnt12uc3GhQl9Rk.NKqPjLpbyYy1gfcUyCm0i"
    passwords["koert"] = "$2a$10\$bTJngWsw6WIwqxjZXj/j7eUBppFfFyt1STE3Q7gO6mO0YS.vbfpsq"
  }

  fun findByUsername(userName: String): ApplicationUser? {
    var applicationUser: ApplicationUser? = null
    if (passwords.containsKey(userName)) {
      applicationUser = ApplicationUser(userName, passwords[userName])
    }
    return applicationUser
  }
}