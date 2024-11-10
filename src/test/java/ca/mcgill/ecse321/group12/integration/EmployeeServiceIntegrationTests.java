package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.AfterTestClass;

import ca.mcgill.ecse321.group12.repository.EmployeeRepository;
import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EmployeeServiceIntegrationTests {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private TestRestTemplate client;

	private final String VALID_NAME = "amyding";

	private final String VALID_EMAIL = "amyding@gmail.ca";

	private final String VALID_EMAIL2 = "amy@gmail.ca";

	private final String VALID_PASSWORD = "password123";

	private final String VALID_PHONENUMBER = "2047989416";

	private int validId;

	private int invalidId = -1;

	/**
	 * Clears database before and after tests
	 * @author Amy Ding
	 * @return void
	 */
	@BeforeAll
	@AfterTestClass
	public void clearDatabase() {
		employeeRepository.deleteAll();
	}

	/**
	 * Test to create an employee with valid inputs
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(1)
	public void testCreateValidEmployee() {
		// Arrange
		EmployeeRequestDto request = new EmployeeRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);

		// Act
		ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request,
				EmployeeResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		EmployeeResponseDto createdEmployee = response.getBody();
		assertNotNull(createdEmployee);
		assertEquals(VALID_NAME, createdEmployee.getName());
		assertEquals(VALID_EMAIL, createdEmployee.getEmail());
		assertEquals(VALID_PHONENUMBER, createdEmployee.getPhoneNumber());
		assertNotNull(createdEmployee.getId());
		assertTrue(createdEmployee.getId() > 0, "Response should have a positive ID.");

		this.validId = createdEmployee.getId();
	}

	/**
	 * Test to create an employee account with an email that is already associated with
	 * another employee account
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(2)
	public void testCreateInvalidEmployee() {
		// Arrange
		EmployeeRequestDto request = new EmployeeRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);

		// Act
		ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request,
				EmployeeResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Test to get an employee account with an id that is valid
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(3)
	public void testReadEmployeeByValidId() {
		// Arrange
		String url = "/employees/" + this.validId;

		// Act
		ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		EmployeeResponseDto employee = response.getBody();
		assertNotNull(employee);
		assertEquals(VALID_NAME, employee.getName());
		assertEquals(VALID_EMAIL, employee.getEmail());
		assertEquals(VALID_PHONENUMBER, employee.getPhoneNumber());
		assertEquals(this.validId, employee.getId());
	}

	/**
	 * Test to get an employee with an invalid id
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(4)
	public void testReadEmployeeByInvalidId() {
		// Arrange
		String url = "/employees/" + this.invalidId;

		// Act
		ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test to update an employee account with valid inputs
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(5)
	public void testUpdateEmployeeByValidInputs() {
		// Arrange
		String url = "/employees/" + this.validId;
		EmployeeRequestDto body = new EmployeeRequestDto(VALID_EMAIL2, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);
		RequestEntity<EmployeeRequestDto> request = RequestEntity.put(url)
			.accept(MediaType.APPLICATION_PROBLEM_JSON)
			.body(body);
		// Act
		ResponseEntity<EmployeeResponseDto> response = client.exchange(url, HttpMethod.PUT, request,
				EmployeeResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		EmployeeResponseDto employee = response.getBody();
		assertNotNull(employee);
		assertEquals(VALID_NAME, employee.getName());
		assertEquals(VALID_EMAIL2, employee.getEmail());
		assertEquals(VALID_PHONENUMBER, employee.getPhoneNumber());
		assertEquals(this.validId, employee.getId());
	}

	/**
	 * Test to update an employee account with an email that is already associated with a
	 * different account
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(6)
	public void testUpdateEmployeeByInvalidEmail() {
		// Creating a new employee
		EmployeeRequestDto request = new EmployeeRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);
		ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/employees", request,
				EmployeeResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Arrange
		String url = "/employees/" + this.validId;
		EmployeeRequestDto body = new EmployeeRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);
		RequestEntity<EmployeeRequestDto> request2 = RequestEntity.put(url)
			.accept(MediaType.APPLICATION_PROBLEM_JSON)
			.body(body);
		// Act
		ResponseEntity<EmployeeResponseDto> response2 = client.exchange(url, HttpMethod.PUT, request2,
				EmployeeResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
	}

	/**
	 * Test to delete an employee with a valid id
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(7)
	public void testDeleteEmployeeByValidId() {
		// Arrange
		String url = "/employees/" + this.validId;

		// Act
		client.delete(url);
		ResponseEntity<EmployeeResponseDto> response = client.getForEntity(url, EmployeeResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test to attempt to delete an employee with an id that doesn't exist in the database
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(8)
	public void testDeleteEmployeeByInvalidId() {
		// Arrange
		String url = "/employees/" + this.invalidId;
		// Act & Assert
		ResponseEntity<String> response = client.exchange(url, HttpMethod.DELETE, null, String.class);
		String error = response.getBody();
		// Assert
		assertNotNull(response);
		assertNotNull(error);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertTrue(error.contains("There is no employee with ID " + this.invalidId + "."));
	}

}