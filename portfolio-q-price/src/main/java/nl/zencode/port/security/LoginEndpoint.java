package nl.zencode.port.security;

import nl.zencode.port.q.price.YahooFinanceRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Koert Zeilstra
 */
@Path("/security")
public class LoginEndpoint {

  @Inject YahooFinanceRepository yahooFinanceRepository;
  @Inject ApplicationUserRepository applicationUserRepository;

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/login")
  public Response login(UserPassword userPassword) {
    String token = null;
    Response response = null;
    if (applicationUserRepository.isAuthenticated(userPassword.userName, userPassword.password)) {
      token = TokenUtils.generateTokenString(userPassword.userName);
      LoginResponse loginResponse = new LoginResponse();
      loginResponse.jwt = token;
      response = Response.ok(loginResponse).header("Authorization", "Bearer " + token).build();
    } else {
      response = Response.status(Response.Status.NOT_FOUND).build();
    }
    return response;
  }

}
