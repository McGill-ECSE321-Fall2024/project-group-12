package ca.mcgill.ecse321.group12;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.model.UserRole;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;
import ca.mcgill.ecse321.group12.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  
  @Autowired
  AuthService authService;

  /**
   * check for a valid token and provide the auth'd user to Controller methods
   * @author James Madden
   */
  @SuppressWarnings("null")
  @Override
  protected void doFilterInternal (HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
    
    // get the auth header from the request
    String authHeader = req.getHeader("Authorization");

    // check whether there is an auth header to look at
    if (authHeader != null) {
      String token = authHeader.replace("Bearer ", "");
      // get the email of the user
      String email = authService.validateToken(token);
      // find that user
      UserRole user = authService.getUserFromEmail(email);

      // set the authentication for controllers to look at
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    filterChain.doFilter(req, resp);

  }

}
