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

import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Wishlist;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerServiceIntegrationTests {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private TestRestTemplate client;

	private final String VALID_NAME = "Cunegonde";

	private final String VALID_EMAIL = "cune@mail.mcgill.ca";

	private final String VALID_EMAIL2 = "hlosthalthethird@mail.mcgill.ca";

	private final String VALID_PASSWORD = "weizhiiiii";

	private final String VALID_PHONENUMBER = "889427879";

	private Cart cart;

	private Wishlist wishlist;

	private int validId;

	private int invalidId = -1;

	/**
	 * Clears database before and after tests
	 * @author Carmin Vidé
	 * @return void
	 */
	@BeforeAll
	@AfterTestClass
	public void clearDatabase() {
		customerRepository.deleteAll();
	}

	/**
	 * Test to create an customer with valid inputs
	 * @author Carmin Vidé
	 */

	// A new cart and wishlist are created when the customer is created: not in the Dto
	@Test
	@Order(1)
	public void testCreateValidCustomer() {
		// Arrange
		CustomerRequestDto request = new CustomerRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);

		// Act
		ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request,
				CustomerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		CustomerResponseDto createdCustomer = response.getBody();
		assertNotNull(createdCustomer);
		assertEquals(VALID_NAME, createdCustomer.getName());
		assertEquals(VALID_EMAIL, createdCustomer.getEmail());
		assertEquals(VALID_PHONENUMBER, createdCustomer.getPhoneNumber());
		assertNotNull(createdCustomer.getId());
		assertNotNull(createdCustomer.getCart());
		assertNotNull(createdCustomer.getWishlist());
		assertTrue(createdCustomer.getId() > 0, "Response should have a positive ID.");
		this.validId = createdCustomer.getId();
	}

	/**
	 * Test to create an customer account with an email that is already associated with
	 * another customer account
	 * @author Carmin Vidé
	 */
	@Test
	@Order(2)
	public void testCreateInvalidCustomer() {
		// Arrange
		CustomerRequestDto request = new CustomerRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);

		// Act
		ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request,
				CustomerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	/**
	 * Test to get an customer account with an id that is valid
	 * @author Carmin Vidé
	 */
	@Test
	@Order(3)
	public void testReadCustomerByValidId() {
		// Arrange
		String url = "/customers/" + this.validId;

		// Act
		ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CustomerResponseDto customer = response.getBody();
		assertNotNull(customer);
		assertEquals(VALID_NAME, customer.getName());
		assertEquals(VALID_EMAIL, customer.getEmail());
		assertEquals(VALID_PHONENUMBER, customer.getPhoneNumber());
		assertEquals(this.validId, customer.getId());
		assertNotNull(customer.getCart());
		assertNotNull(customer.getWishlist());
	}

	/**
	 * Test to get an customer with an invalid id
	 * @author Carmin Vidé
	 */
	@Test
	@Order(4)
	public void testReadCustomerByInvalidId() {
		// Arrange
		String url = "/customers/" + this.invalidId;

		// Act
		ResponseEntity<CustomerResponseDto> response = client.getForEntity(url, CustomerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test to update an customer account with valid inputs and an id that is valid in the
	 * database
	 * @author Carmin Vidé
	 */
	@Test
	@Order(5)
	public void testUpdateCustomerByValidInputs() {
		// Arrange
		String url = "/customers/" + this.validId;
		CustomerRequestDto body = new CustomerRequestDto(VALID_EMAIL2, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);
		RequestEntity<CustomerRequestDto> request = RequestEntity.put(url)
			.accept(MediaType.APPLICATION_PROBLEM_JSON)
			.body(body);
		// Act
		ResponseEntity<CustomerResponseDto> response = client.exchange(url, HttpMethod.PUT, request,
				CustomerResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CustomerResponseDto customer = response.getBody();
		assertNotNull(customer);
		assertEquals(VALID_NAME, customer.getName());
		assertEquals(VALID_EMAIL2, customer.getEmail());
		assertEquals(VALID_PHONENUMBER, customer.getPhoneNumber());
		assertEquals(this.validId, customer.getId());
		assertNotNull(customer.getCart());
		assertNotNull(customer.getWishlist());
	}

	/**
	 * Test to update an customer account with an email that is already associated with a
	 * different account
	 * @author Carmin Vidé
	 */
	@Test
	@Order(6)
	public void testUpdateCustomerByInvalidEmail() {
		// Creating a new customer
		CustomerRequestDto request = new CustomerRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER);
		ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request,
				CustomerResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Arrange
		String url = "/customers/" + this.validId;
		CustomerRequestDto body = new CustomerRequestDto(VALID_EMAIL, VALID_PASSWORD, VALID_NAME, VALID_PHONENUMBER,
				this.cart, this.wishlist);
		RequestEntity<CustomerRequestDto> request2 = RequestEntity.put(url)
			.accept(MediaType.APPLICATION_PROBLEM_JSON)
			.body(body);
		// Act
		ResponseEntity<CustomerResponseDto> response2 = client.exchange(url, HttpMethod.PUT, request2,
				CustomerResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
	}

	/**
	 * Test to delete an customer with a valid id
	 * @author Carmin Vidé
	 */
	@Test
	@Order(7)
	public void testDeleteCustomerByValidId() {
		// Arrange
		String url = "/customers/" + this.validId;

		// Act
		ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	/**
	 * Test to attempt to delete an customer with an id that doesn't exist in the database
	 * @author Carmin Vidé
	 */
	@Test
	@Order(8)
	public void testDeleteCustomerByInvalidId() {
		// Arrange
		String url = "/customers/-1";
		// Act & Assert
		ResponseEntity<Void> response = client.exchange(url, HttpMethod.DELETE, null, Void.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

}