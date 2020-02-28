package nl.zencode.port.stock.security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * @author Koert Zeilstra
 *
 * See https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
 */
@EnableWebSecurity
class WebSecurity(private val userDetailsService: UserDetailsServiceImpl,
                  private val bCryptPasswordEncoder: BCryptPasswordEncoder) : WebSecurityConfigurerAdapter() {
  @Throws(Exception::class)
  override fun configure(http: HttpSecurity) {
    http.cors().and().csrf().disable().authorizeRequests()
       .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
       .antMatchers(HttpMethod.GET, SecurityConstants.HEALTH_URL).permitAll()
       .anyRequest().authenticated()
       .and() //        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
       .addFilter(JWTAuthorizationFilter(authenticationManager())) // this disables session creation on Spring Security
       .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
  }

  @Throws(Exception::class)
  public override fun configure(auth: AuthenticationManagerBuilder) {
    auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
  }

  @Bean
  fun corsConfigurationSource(): CorsConfigurationSource {
    val source = UrlBasedCorsConfigurationSource()
    source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
    return source
  }

}