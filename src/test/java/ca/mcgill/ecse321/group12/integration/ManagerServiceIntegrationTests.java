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

import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.group12.dto.ManagerRequestDto;
import ca.mcgill.ecse321.group12.dto.ManagerResponseDto;
import ca.mcgill.ecse321.group12.repository.ManagerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ManagerServiceIntegrationTests {

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private TestRestTemplate client;

	private final String VALID_NAME = "iamthemanager";

	private final String VALID_EMAIL = "manager@gmail.ca";

	private final String VALID_EMAIL2 = "manager@gmail.ca";

	private final String VALID_PASSWORD = "managerpassword123";

	private final String VALID_PHONENUMBER = "2047989416";

	/**
	 * Clears database before and after tests
	 * @author Amy Ding
	 * @return void
	 */
	@BeforeAll
	@AfterTestClass
	public void clearDatabase() {
		managerRepository.deleteAll();
	}

	/**
	 * Test to create a manager successfully
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(2)
	public void testCreateManagerSuccessfully() {
		// Arrange
		ManagerRequestDto request = new ManagerRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);

		// Act
		ResponseEntity<ManagerResponseDto> response = client.postForEntity("/manager", request,
				ManagerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		ManagerResponseDto createdManager = response.getBody();
		assertNotNull(createdManager);
		assertEquals(VALID_NAME, createdManager.getName());
		assertEquals(VALID_EMAIL, createdManager.getEmail());
		assertEquals(VALID_PHONENUMBER, createdManager.getPhoneNumber());
		assertNotNull(createdManager.getId());
		assertTrue(createdManager.getId() > 0, "Response should have a positive ID.");
	}

	/**
	 * Test to create a manager account when there is already a manager account active
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(3)
	public void testCreateManagerWhenExistingManager() {
		// Arrange
		ManagerRequestDto request = new ManagerRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);

		// Act
		ResponseEntity<String> response = client.postForEntity("/manager", request, String.class);
		String error = response.getBody();

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(error);
		assertTrue(error.contains("Create manager failed. Manager already exists"));
	}

	/**
	 * Test to get an manager when there is no manager
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(1)
	public void testReadManagerWhenNoManager() {
		// Arrange
		String url = "/manager";

		// Act
		ResponseEntity<String> response = client.getForEntity(url, String.class);
		String error = response.getBody();

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(error);
		assertTrue(error.contains("Get manager failed. No manager account exists"));
	}

	/**
	 * Test to get an manager successfully
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(4)
	public void testReadManagerSuccessfully() {
		// Arrange
		String url = "/manager";

		// Act
		ResponseEntity<ManagerResponseDto> response = client.getForEntity(url, ManagerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ManagerResponseDto manager = response.getBody();
		assertNotNull(manager);
		assertEquals(VALID_NAME, manager.getName());
		assertEquals(VALID_EMAIL, manager.getEmail());
		assertEquals(VALID_PHONENUMBER, manager.getPhoneNumber());
	}

	/**
	 * Test to update a manager account successfully
	 * @author Amy Ding
	 * @return void
	 */
	@Test
	@Order(5)
	public void testUpdateManagerSuccessfully() {
		// Arrange
		String url = "/manager";
		ManagerRequestDto body = new ManagerRequestDto(VALID_EMAIL2, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);
		RequestEntity<ManagerRequestDto> request = RequestEntity.put(url)
			.accept(MediaType.APPLICATION_PROBLEM_JSON)
			.body(body);

		// Act
		ResponseEntity<ManagerResponseDto> response = client.exchange(url, HttpMethod.PUT, request,
				ManagerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ManagerResponseDto manager = response.getBody();

		assertNotNull(manager);
		assertEquals(VALID_NAME, manager.getName());
		assertEquals(VALID_EMAIL2, manager.getEmail());
		assertEquals(VALID_PHONENUMBER, manager.getPhoneNumber());
	}

}