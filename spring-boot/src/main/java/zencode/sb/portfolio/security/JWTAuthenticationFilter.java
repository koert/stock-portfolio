package zencode.sb.portfolio.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static zencode.sb.portfolio.security.SecurityConstants.SECRET;

/**
 * @author Koert Zeilstra
 */
public class JWTAuthenticationFilter extends org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
    Authentication authentication = null;
    try {
      String userName = req.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY);
      String password = req.getParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY);
      if (userName == null) {
        ApplicationUser creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUserName(),
            creds.getPassword(), new ArrayList<>()));
      } else {
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password, new ArrayList<>()));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return authentication;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
      Authentication authentication) {
    String token = JWT.create()
        .withSubject(((User) authentication.getPrincipal()).getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
        .sign(HMAC512(SECRET.getBytes()));
    res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
  }
}
