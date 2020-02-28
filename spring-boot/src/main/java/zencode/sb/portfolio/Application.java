package zencode.sb.portfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Koert Zeilstra
 */
@SpringBootApplication
@ServletComponentScan
@RestController
public class Application {

  private static final Logger log = LoggerFactory.getLogger(Application.class);

//  @RequestMapping("/hello")
//  public String home() {
//    return "Hello Docker World";
//  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    return builder.build();
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}