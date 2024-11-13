package ca.mcgill.ecse321.group12.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ca.mcgill.ecse321.group12.SecurityFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig {
  
  @Autowired
  SecurityFilter securityFilter;

  /**
   * defines which methods require authentication, and which level of authorization is required to use it
   * @author James Madden
   */
  @Bean
  SecurityFilterChain secuirtyFilterChain(HttpSecurity httpSecurity) throws Exception {
    
    // TODO: define for all endpoints
    // right now, all methods are public except getting and creating orders
    return httpSecurity
      .csrf(csrf -> csrf.disable())
      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(HttpMethod.POST, "/auth/*").permitAll()
        // anyone can create a customer: it's just signing up
        .requestMatchers(HttpMethod.POST, "/customers").permitAll()
        .requestMatchers(HttpMethod.GET, "/orders/*").hasRole("USER")
        .requestMatchers(HttpMethod.POST, "/orders").hasRole("CUSTOMER")
        .anyRequest().permitAll())
      .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
      .build();

  }

  @Bean
  AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

    return authenticationConfiguration.getAuthenticationManager();

  }

  /**
   * create an object that can be used to encrypt the password
   * @author James Madden
   */
  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
