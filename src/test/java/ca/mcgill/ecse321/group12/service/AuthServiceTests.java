package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.group12.exception.CustomException;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.model.Manager;
import ca.mcgill.ecse321.group12.model.UserRole;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;

@SpringBootTest
public class AuthServiceTests {
  
  @Mock
  private CustomerRepository customerRepo;
  @Mock
  private EmployeeRepository employeeRepo;
  @Mock
  private ManagerRepository managerRepo;

  @InjectMocks
  private AuthService authService;

  /**
   * Test that an auth token is successfully created, and that the account
   * details can be extracted from it.
   * @author James Madden
   */
  @Test
  public void testGenerateAuthToken() {

    // create a customer
    Customer customer = new Customer();
    customer.setEmail("customer2004@email.com");
    customer.setPassword("secure_password@123");
    // create the auth token
    String authToken = authService.generateAuthToken(customer);
    // now get the email back from the token
    String emailFromToken = authService.validateToken(authToken);
    // check that the email is correct
    assertNotNull(authToken);
    assertNotNull(emailFromToken);
    assertEquals(customer.getEmail(), emailFromToken);

  }

  /**
   * test that the system can correctly return a user from their email.
   * @author James Madden
   */
  @Test 
  public void testSuccessfullyGetUserFromEmail() {

    // create an employee
    Employee employee = new Employee();
    employee.setEmail("employee@email.com");
    employee.setPassword("secret_password");

    // mock the repo methods
    when(managerRepo.findManagerByEmail(employee.getEmail())).thenReturn(null);
    when(employeeRepo.findEmployeeByEmail(employee.getEmail())).thenReturn(employee);
    when(customerRepo.findCustomerByEmail(employee.getEmail())).thenReturn(null);

    // try to get the user
    UserRole foundUser = authService.getUserFromEmail(employee.getEmail());

    // check the user is correct
    assertNotNull(foundUser);
    assertEquals(Employee.class, foundUser.getClass());
    assertEquals(employee.getEmail(), foundUser.getEmail());
    assertEquals(employee.getPassword(), foundUser.getPassword());
    assertEquals(employee.getUserType(), foundUser.getUserType());

  }

  /**
   * try to find a user with an email that doesn't exist.
   * @author James Madden
   */
  @Test
  public void testUnsuccessfullyGetUserFromEmail() {

    String email = "fake_email@email.com";

    // mock the repo methods
    when(managerRepo.findManagerByEmail(email)).thenReturn(null);
    when(employeeRepo.findEmployeeByEmail(email)).thenReturn(null);
    when(customerRepo.findCustomerByEmail(email)).thenReturn(null);

    // try to get a user
    CustomException error = assertThrows(CustomException.class, () -> authService.getUserFromEmail(email));
    assertEquals(HttpStatus.BAD_REQUEST, error.getStatus());
    assertEquals("user " + email + " not found.", error.getMessage());

  }

  /**
   * test that the matchUserAndToken method successfully identifies a match.
   * @author James Madden
   */
  @Test
  public void testSuccessfullyMatchUserAndToken() {

    // create a manager
    Manager manager = new Manager();
    manager.setEmail("manager@company.com");
    manager.setPassword("secret_code");

    // create the auth token
    String auth = authService.generateAuthToken(manager);

    // make sure the matchUserAndToken method does not throw
    // if it does throw an error, the test will fail
    authService.matchUserAndToken(manager, auth);

  }

  /**
   * make sure that the matchUserAndToken will throw if a different user and auth code are provided
   * @author James Madden
   */
  public void testUnsuccessfullyMatchUserAndToken() {

    // create 2 customers
    Customer customer1 = new Customer();
    Customer customer2 = new Customer();
    customer1.setEmail("uno@gmail.com");
    customer1.setPassword("password123");
    customer2.setEmail("dos@gmail.com");
    customer2.setPassword("secret");

    // create an auth token for customer 1
    String auth = authService.generateAuthToken(customer1);

    // try to match the auth token and the second user
    CustomException error = assertThrows(CustomException.class, () -> authService.matchUserAndToken(customer2, auth));
    assertEquals(HttpStatus.FORBIDDEN, error.getStatus());
    assertEquals("You are not authorized to take this action for this user.", error.getMessage());

  }

}
