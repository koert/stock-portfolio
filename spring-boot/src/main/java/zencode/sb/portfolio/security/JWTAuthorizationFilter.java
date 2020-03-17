package zencode.sb.portfolio.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;

/**
 * @author Koert Zeilstra
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authManager) {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
    String header = req.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
    if (token != null) {
      String jwt = token.replace(SecurityConstants.TOKEN_PREFIX, "");
      String user = null;
      try {
        PublicKey publicKey = readPublicKey("/publicKey.pem");
        Verification verification = JWT.require(Algorithm.RSA256((RSAPublicKey) publicKey, null));
        DecodedJWT decodedJWT = verification.build().verify(jwt);
        user = decodedJWT.getSubject();
        if (user != null) {
          usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      if (user == null) {
        try {
          user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
              .build()
              .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
              .getSubject();

          if (user != null) {
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
          }
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
    return usernamePasswordAuthenticationToken;
  }

  /**
   * Read a PEM encoded private key from the classpath
   *
   * @param pemResName - key file resource name
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
    InputStream contentIS = JWTAuthorizationFilter.class.getResourceAsStream(pemResName);
    byte[] tmp = new byte[4096];
    int length = contentIS.read(tmp);
    return decodePrivateKey(new String(tmp, 0, length, "UTF-8"));
  }

  /**
   * Read a PEM encoded public key from the classpath
   *
   * @param pemResName - key file resource name
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PublicKey readPublicKey(final String pemResName) throws Exception {
    InputStream contentIS = JWTAuthorizationFilter.class.getResourceAsStream(pemResName);
    byte[] tmp = new byte[4096];
    int length = contentIS.read(tmp);
    return decodePublicKey(new String(tmp, 0, length, "UTF-8"));
  }

  /**
   * Decode a PEM encoded private key string to an RSA PrivateKey
   *
   * @param pemEncoded - PEM string for private key
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePrivate(keySpec);
  }

  /**
   * Decode a PEM encoded private key string to an RSA PrivateKey
   *
   * @param pemEncoded - PEM string for private key
   * @return PrivateKey
   * @throws Exception on decode failure
   */
  public static PublicKey decodePublicKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = toEncodedBytes(pemEncoded);

    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encodedBytes);
    KeyFactory kf = KeyFactory.getInstance("RSA");
    return kf.generatePublic(keySpec);
  }

  private static byte[] toEncodedBytes(final String pemEncoded) {
    final String normalizedPem = removeBeginEnd(pemEncoded);
    return Base64.getDecoder().decode(normalizedPem);
  }

  private static String removeBeginEnd(String pem) {
    pem = pem.replaceAll("-----BEGIN (.*)-----", "");
    pem = pem.replaceAll("-----END (.*)----", "");
    pem = pem.replaceAll("\r\n", "");
    pem = pem.replaceAll("\n", "");
    return pem.trim();
  }

}
