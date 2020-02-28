package nl.zencode.port.stock.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

/**
 * @author Koert Zeilstra
 */
@Component
class AuthenticationSession {
  val authentication: Authentication
    get() = SecurityContextHolder.getContext().authentication

  val userId: String
    get() = SecurityContextHolder.getContext().authentication.principal as String
}