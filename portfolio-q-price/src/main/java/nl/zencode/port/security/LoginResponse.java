package nl.zencode.port.security;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * @author Koert Zeilstra
 */
@RegisterForReflection
public class LoginResponse {
  public String jwt;
}
