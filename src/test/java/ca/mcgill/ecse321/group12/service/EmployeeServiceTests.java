package ca.mcgill.ecse321.group12.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.group12.model.Employee;
import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import ca.mcgill.ecse321.group12.exception.CustomException;

@SpringBootTest
public class EmployeeServiceTests {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	/**
	 * Test to create an employee with valid inputs
	 * @author Amy Ding
	 * @return void
	 */
	@SuppressWarnings("null")
	@Test
	public void testCreateValidEmployee() {
		// Arrange
		String name = "amy";
		String email = "ha@mail.mcgill.ca";
		String password = "12345678";
		String phoneNumber = "2041123455";
		Employee employee = new Employee();

		employee.setEmail(email);
		employee.setPassword(password);
		employee.setName(name);
		employee.setPhoneNumber(phoneNumber);

		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		// Act
		Employee createdEmployee = employeeService.createEmployee(email, password, name, phoneNumber);

		// Assert
		assertNotNull(createdEmployee);
		assertEquals(name, createdEmployee.getName());
		assertEquals(email, createdEmployee.getEmail());
		assertEquals(password, createdEmployee.getPassword());
		assertEquals(phoneNumber, createdEmployee.getPhoneNumber());
		verify(employeeRepository, times(1)).save(any(Employee.class));
	}

	/**
	 * Test to create an employee account with an email that is already associated with
	 * another employee account
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testCreateEmployeeWithInvalidEmail() {
		// Arrange
		String name = "amy";
		String email = null;
		String password = "12345678";
		String phoneNumber = "2041123455";

		Employee employee = new Employee();

		employee.setEmail(email);
		employee.setPassword(password);
		employee.setName(name);
		employee.setPhoneNumber(phoneNumber);

		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		// Act
		// make sure the employee was created successfully
		// set employee's email to null to simulate what happens when employee was already
		// taken
		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> employeeService.createEmployee(email, password, name, phoneNumber));
		assertEquals("Create employee failed. Employee with this email already exists in the system.", e.getMessage());
	}

	/**
	 * Test to get an employee account with an id that is valid
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testReadEmployeeByValidId() {
		// Arrange
		int id = 42;
		Employee employee = new Employee();
		employee.setEmail("email@mail.mcgill.ca");
		employee.setName("johnny");
		employee.setPassword("123456");
		employee.setPhoneNumber("2041234567");
		when(employeeRepository.findEmployeeById(id)).thenReturn(employee);

		// Act
		Employee foundEmployee = employeeService.findEmployeeById(id);

		// Assert
		assertNotNull(employee);
		assertEquals(employee.getName(), foundEmployee.getName());
		assertEquals(employee.getEmail(), foundEmployee.getEmail());
		assertEquals(employee.getPassword(), foundEmployee.getPassword());
		assertEquals(employee.getPhoneNumber(), foundEmployee.getPhoneNumber());
	}

	/**
	 * Test to get an employee with an invalid id
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testReadEmployeeByInvalidId() {
		// Arrange
		int id = -1;
		when(employeeRepository.findEmployeeById(id)).thenReturn(null);

		// Act
		// Assert
		CustomException e = assertThrows(CustomException.class, () -> employeeService.findEmployeeById(id));
		assertEquals("There is no employee with ID " + id + ".", e.getMessage());
	}

	/**
	 * Test to update an employee account with valid inputs
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testUpdateEmployeeByValidArguments() {
		// Arrange
		int id = 42;
		Employee employee = new Employee();
		String email = "ea@mail.mcgill.ca";
		String name = "johnny";
		String password = "123456";
		String phoneNumber = "2041234567";

		employee.setId(id);
		employee.setEmail(email);
		employee.setName(name);
		employee.setPassword(password);
		employee.setPhoneNumber(phoneNumber);

		String newEmail = "newemail@mail.mcgill.ca";
		String newName = "john";
		String newPassword = "123456";
		String newPhoneNumber = "2047654321";

		when(employeeRepository.findEmployeeById(id)).thenReturn(employee);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		// Act
		employeeService.createEmployee(email, password, name, phoneNumber);
		employeeService.updateEmployeeById(id, newEmail, newPassword, newName, newPhoneNumber);

		// Assert
		assertNotNull(employee);
		assertEquals(newEmail, employee.getEmail());
		assertEquals(newName, employee.getName());
		assertEquals(newPassword, employee.getPassword());
		assertEquals(newPhoneNumber, employee.getPhoneNumber());
	}

	/**
	 * Test to update an employee account with an email that is already associated with a
	 * different account
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testUpdateEmployeeByInvalidEmail() {
		// Arrange
		int id = 42;
		Employee employee = new Employee();
		String name = "amy";
		String email = "amy_d@mail.mcgill.ca";
		String password = "12345678";
		String phoneNumber = "2041123455";

		String name2 = "jogn";
		String email2 = "lol@gmail.com";
		String password2 = "123";
		String phoneNumber2 = "123456";

		employee.setEmail(email);
		employee.setName(name);
		employee.setPassword(password);
		employee.setPhoneNumber(phoneNumber);
		when(employeeRepository.findEmployeeById(id)).thenReturn(employee);
		when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

		// Act
		employeeService.createEmployee(email, password, name, phoneNumber);
		employeeService.createEmployee(email2, password2, name2, phoneNumber2);

		// Assert
		CustomException e = assertThrows(CustomException.class,
				() -> employeeService.updateEmployeeById(id, email2, password, name, phoneNumber));
		assertEquals("Update employee failed. Employee with this email already exists in the system.", e.getMessage());
	}

	/**
	 * Test to delete an employee with a valid id
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testDeleteEmployeeByValidId() {
		// Arrange
		int id = 42;
		Employee employee = new Employee();
		employee.setEmail("email@mail.mcgill.ca");
		employee.setName("johnny");
		employee.setPassword("123456");
		employee.setPhoneNumber("2041234567");

		when(employeeRepository.findEmployeeById(id)).thenReturn(employee);

		// Act
		employeeService.deleteEmployeeById(id);

		// now try to get the employee
		when(employeeRepository.findEmployeeById(id)).thenReturn(null);
		// this should throw an exception
		CustomException e = assertThrows(CustomException.class, () -> employeeService.findEmployeeById(id));
		// Assert
		assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
		assertEquals("There is no employee with ID " + id + ".", e.getMessage());
	}

	/**
	 * Test to attempt to delete an employee with an id that doesn't exist in the database
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	public void testDeleteEmployeeByInvalidId() {
		// Arrange
		int id = 100;
		when(employeeRepository.findEmployeeById(id)).thenReturn(null);
		// Act
		CustomException e = assertThrows(CustomException.class, () -> employeeService.deleteEmployeeById(id));
		// Assert
		assertEquals("There is no employee with ID " + id + ".", e.getMessage());
	}

}