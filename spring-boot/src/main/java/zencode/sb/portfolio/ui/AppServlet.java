package zencode.sb.portfolio.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Koert Zeilstra
 */
@WebServlet(urlPatterns = "/a/*", loadOnStartup = 1)
public class AppServlet extends HttpServlet {

  private static final Logger log = LoggerFactory.getLogger(AppServlet.class);

  @Override
  protected void service(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
    String pathInfo = request.getPathInfo();
    String forwardPath = null;
    if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
      forwardPath = "/index.html";
    } else {
      Pattern pattern = Pattern.compile("/([^/]+)(/.*)?");
      Matcher matcher = pattern.matcher(pathInfo);
      if (matcher.matches()) {
        String appNaam = matcher.group(1);
        forwardPath = "/index.html"; //assetUri + "/" + appNaam + ".html";
        String servletPath = request.getServletPath();
        String path = servletPath + "/" + appNaam;
        log.debug("appNaam: {}", appNaam);
        if (matcher.groupCount() > 1) {
          path = path + matcher.group(2);
        }
        log.debug("path: {}", path);
      } else {
        forwardPath = "/index.html";
      }
    }
    RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
    dispatcher.forward(request, response);
  }

}
