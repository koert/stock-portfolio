package nl.zencode.port.stock.security

/**
 * @author Koert Zeilstra
 */
object SecurityConstants {
  const val SECRET = "SecretKeyToGenJWTs"
  const val EXPIRATION_TIME: Long = 864000000 // 10 days
  const val TOKEN_PREFIX = "Bearer "
  const val HEADER_STRING = "Authorization"
  const val SIGN_UP_URL = "/users/sign-up"
  const val HEALTH_URL = "/health"
}