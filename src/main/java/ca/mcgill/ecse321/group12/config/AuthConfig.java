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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ca.mcgill.ecse321.group12.SecurityFilter;

@Configuration
@EnableWebSecurity
public class AuthConfig {

	@Autowired
	SecurityFilter securityFilter;

	/**
	 * defines which methods require authentication, and which level of authorization is
	 * required to use it
	 * @author James Madden
	 */
	@Bean
	SecurityFilterChain secuirtyFilterChain(HttpSecurity httpSecurity) throws Exception {

		// see
		// https://github.com/McGill-ECSE321-Fall2024/project-group-12/wiki/API-Endpoints
		// for the list of endpoints
		return httpSecurity.csrf(csrf -> csrf.disable())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorize -> authorize
				// all auth endpoints should be public - no token required
				.requestMatchers(HttpMethod.POST, "/auth/*")
				.permitAll()
				// cart endpoints should be protected to the customer involved
				.requestMatchers(HttpMethod.GET, "/cart/*")
				.hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.PUT, "/cart/*")
				.hasRole("CUSTOMER")
				// comments: anyone can get them (to see), only users can post, put, or
				// delete
				.requestMatchers(HttpMethod.GET, "/comments")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/comments/*")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/comments")
				.hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/comments/*")
				.hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/comments/*")
				.hasRole("USER")
				// customer: anyone can create (signing up!), only employees can get all,
				// and only users can get/modify specific
				.requestMatchers(HttpMethod.POST, "/customers")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/customers")
				.hasRole("EMPLOYEE")
				.requestMatchers(HttpMethod.GET, "/customers/*")
				.hasRole("USER")
				.requestMatchers(HttpMethod.DELETE, "/customers/*")
				.hasRole("USER")
				.requestMatchers(HttpMethod.PUT, "/customers/*")
				.hasRole("CUSTOMER")
				// employees: only managers or employees should be permitted to use these
				// endpoints,
				// but for testing, allow anyone
				// no need to do anything since they'll fall under the catchall at the
				// bottom
				// games: anyone can get games, but only employees can post and put games
				.requestMatchers(HttpMethod.GET, "/games")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/games/*")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/games")
				.hasRole("EMPLOYEE")
				.requestMatchers(HttpMethod.PUT, "/games/*")
				.hasRole("EMPLOYEE")
				// orders: users can get orders, customers can create or return orders.
				.requestMatchers(HttpMethod.GET, "/orders/*")
				.hasRole("USER")
				.requestMatchers(HttpMethod.POST, "/orders")
				.hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.PUT, "/orders/*")
				.hasRole("CUSTOMER")
				// reviews: anyone can get, customers can create or modify
				.requestMatchers(HttpMethod.GET, "/reviews")
				.permitAll()
				.requestMatchers(HttpMethod.GET, "/reviews/*")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/reviews")
				.hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.PUT, "/reviews/*")
				.hasRole("CUSTOMER")
				// wishlist: only customer
				.requestMatchers(HttpMethod.GET, "/reviews/*")
				.hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.PUT, "/reviews/*")
				.hasRole("CUSTOMER")

				.anyRequest()
				.permitAll())
			.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
			.build();

	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();

	}

}
