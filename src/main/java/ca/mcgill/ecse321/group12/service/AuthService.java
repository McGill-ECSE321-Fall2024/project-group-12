package ca.mcgill.ecse321.group12.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.model.UserRole;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;

@Service
public class AuthService {

  @Autowired
  private ManagerRepository managerRepo;
  @Autowired
  private EmployeeRepository employeeRepo;
  @Autowired
  private CustomerRepository customerRepo;
  
  // get the secret key from the .env file
  @Value("${security.jwt.token.secret-key}")
  private String JWT_SECRET;

  /**
   * create an authorization token using JWT
   * @author James Madden
   */
  public String generateAuthToken(UserRole user) {
    
    Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
    return JWT.create()
      .withSubject(user.getEmail())
      .withClaim("username", user.getEmail())
      .withExpiresAt(generateExpiryDate())
      .sign(algorithm);

  }

  /**
   * check whether the provided token is valid
   * @returns user's email
   * @author James Madden
   */
  public String validateToken(String token) {

    Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
    return JWT.require(algorithm)
      .build()
      .verify(token)
      .getSubject();

  }

  /**
   * figure out the time when the auth token should run out
   * @author James Madden
   */
  public Instant generateExpiryDate() {
    // TODO: make the time until expiry longer.
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
  }

  /**
   * find a user from the appropriate respository.
   * @author James Madden
   */
  public UserRole getUserFromEmail(String email) {
    
    UserRole user;
    Manager manager = managerRepo.findManagerByEmail(email);
    Employee employee = employeeRepo.findEmployeeByEmail(email);
    Customer customer = customerRepo.findCustomerByEmail(email);
    if (manager != null) {
      user = manager;
    } else if (employee != null) {
      user = employee;
    } else {
      user = customer;
    }

    if (user == null) {
      throw new CustomException(HttpStatus.BAD_REQUEST, "user " + email + " not found.");
    }

    return user;

  }

  /**
   * check that the auth token sent relates to the user that the request is targetting.
   * this is to ensure that for example, nobody can edit a user except themselves.
   * @author James Madden
   */
  public void matchUserAndToken(UserRole user, String token) {

    // remove the Bearer prefix from the token
    token = token.replace("Bearer ", "");
    // get the email and validate the token
    String email = validateToken(token);
    // compare the emails. if they're not the same, error
    if (!email.equals(user.getEmail())) {
      throw new CustomException(HttpStatus.FORBIDDEN, "You are not authorized to take this action for this user.");
    }

  }

}
