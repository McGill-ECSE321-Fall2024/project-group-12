package ca.mcgill.ecse321.group12.integration;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import ca.mcgill.ecse321.group12.dto.AuthRequestDto;
import ca.mcgill.ecse321.group12.dto.AuthResponseDto;
import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerCreateResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.group12.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CartServiceIntegrationTests {

	@Autowired
	private TestRestTemplate client;

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private GameRepository gameRepo;

	@Autowired
	private CustomerRepository customerRepo;

	private int validId;

	private CustomerCreateResponseDto customer;

	private GameResponseDto game;

	// the auth token to allow requests for this user
	private String customerAuth;

	private String employeeAuth;

	@BeforeAll
	public void setup() {
		// Create (POST) a customer to use their cart for tests
		CustomerRequestDto customerRequest = new CustomerRequestDto("customer@gmail.com", "password", "Customer",
				"889427879", "1234 Street");
		ResponseEntity<CustomerCreateResponseDto> customerResponse = client.postForEntity("/customers", customerRequest,
				CustomerCreateResponseDto.class);
		// Save the response
		this.customer = customerResponse.getBody();
		// set the auth string for use in request headers
		customerAuth = "Bearer " + this.customer.getToken();
		// create (POST) an employee to use their authorization header for creating games
		EmployeeRequestDto employeeRequest = new EmployeeRequestDto();
		employeeRequest.setName("Employee 1");
		employeeRequest.setEmail("employee@company.com");
		employeeRequest.setPassword("password");
		employeeRequest.setPhoneNumber("604 000 0000");
		ResponseEntity<EmployeeResponseDto> employeeResponse = client.postForEntity("/employees", employeeRequest,
				EmployeeResponseDto.class);
		assertEquals(HttpStatus.CREATED, employeeResponse.getStatusCode());
		// use the auth endpoint to get a token for the employee
		AuthRequestDto authRequest = new AuthRequestDto();
		authRequest.setEmail(employeeRequest.getEmail());
		authRequest.setPassword(employeeRequest.getPassword());
		ResponseEntity<AuthResponseDto> authResponse = client.postForEntity("/auth/signin", authRequest,
				AuthResponseDto.class);
		assertEquals(HttpStatus.OK, authResponse.getStatusCode());
		// store the token
		AuthResponseDto auth = authResponse.getBody();
		assertNotNull(auth);
		assertNotNull(auth.getToken());
		employeeAuth = "Bearer " + auth.getToken();
		// Create (POST) a game to use it for tests
		GameRequestDto gameRequest = new GameRequestDto(Category.Action, Console.PC, 1, 1.0f, "Game Name...",
				"Game Description...", GameStatus.Archived, 2020);
		RequestEntity<GameRequestDto> gameReq = RequestEntity.post("/games")
			.header("Authorization", employeeAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(gameRequest);
		ResponseEntity<GameResponseDto> gameResponse = client.exchange(gameReq, GameResponseDto.class);
		// ResponseEntity<GameResponseDto> gameResponse = client.postForEntity("/games",
		// gameRequest,
		// GameResponseDto.class);
		// Save the response
		assertEquals(HttpStatus.CREATED, gameResponse.getStatusCode());
		this.game = gameResponse.getBody();
	}

	@AfterAll
	public void clearDatabase() {
		customerRepo.deleteAll();
		cartRepo.deleteAll();
		gameRepo.deleteAll();
	}

	/**
	 * Test finding a cart by a valid ID.
	 * @author Sophia
	 */
	@Test
	@Order(1)
	public void testFindCartByValidId() {
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;

		// Act
		// provide the auth header
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CartResponseDto> response = client.exchange(req, CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		// Check that the cart found has the correct ID
		assertEquals(this.validId, cart.getId());
	}

	/**
	 * Test finding a cart by an invalid ID.
	 * @author Sophia
	 */
	@Test
	@Order(2)
	public void testFindCartByInvalidId() {
		// Arrange
		this.validId = this.customer.getCart().getId();
		int invalidId = this.validId + 1; // + 1 to the valid ID to ensure it's not the
											// valid id (and thus invalid)
		String url = "/cart/" + invalidId;

		// Act
		RequestEntity<Void> req = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CartResponseDto> response = client.exchange(req, CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test adding a game to cart by invalid game id.
	 * @author Sophia
	 */
	@Test
	@Order(3)
	public void testAddGameToCartByInvalidId() {
		// Start with an empty cart, add a game to the cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;

		// Act
		// Add the game ID (of the game to be added to cart) to the request body
		CartRequestDto body = new CartRequestDto();
		body.setGameId(this.game.getId() + 1); // + 1 to the game ID to ensure it's not
												// the valid id (and thus invalid)
		RequestEntity<CartRequestDto> CartRequestEntity = RequestEntity.put(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		// PUT request
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.PUT, CartRequestEntity,
				CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	/**
	 * Test adding a game to cart, with a valid game id.
	 * @author Sophia
	 */
	@Test
	@Order(4)
	public void testAddGameToCartByValidId() {
		// Start with an empty cart, add a game to the cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;

		// Act
		// Add the game ID (of the game to be added to cart) to the request body
		CartRequestDto body = new CartRequestDto();
		body.setGameId(this.game.getId());
		RequestEntity<CartRequestDto> CartRequestEntity = RequestEntity.put(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		// PUT request
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.PUT, CartRequestEntity,
				CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		// Check each game attribute
		assertEquals(this.game.getCategory(), cart.getGames().get(0).getCategory());
		assertEquals(this.game.getConsole(), cart.getGames().get(0).getConsole());
		assertEquals(this.game.getInventory(), cart.getGames().get(0).getInventory());
		assertEquals(this.game.getPrice(), cart.getGames().get(0).getPrice());
		assertEquals(this.game.getName(), cart.getGames().get(0).getName());
		assertEquals(this.game.getDescription(), cart.getGames().get(0).getDescription());
		assertEquals(this.game.getStatus(), cart.getGames().get(0).getStatus());
		assertEquals(this.game.getId(), cart.getGames().get(0).getId());
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(5)
	public void testRemoveGameFromCartByInvalidId() {
		// Start with non-empty cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;
		RequestEntity<Void> CartRequestEntity1 = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CartResponseDto> response = client.exchange(CartRequestEntity1, CartResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		List<GameResponseDto> games = cart.getGames();
		int wrongGameId = games.get(0).getId() + 1;

		RequestEntity<Void> CartRequestEntity2 = RequestEntity.put(url + "?remove=" + wrongGameId)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		// Act
		// Remove a game with an invalid ID
		ResponseEntity<CartResponseDto> response2 = client.exchange(CartRequestEntity2, CartResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());

	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(6)
	public void testRemoveGameFromCartByInvalidString() {
		// Start with non-empty cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;
		RequestEntity<Void> CartRequestEntity1 = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CartResponseDto> response = client.exchange(CartRequestEntity1, CartResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Act
		// Remove a game with an invalid ID
		RequestEntity<Void> CartRequestEntity2 = RequestEntity.put(url + "?remove=" + "wrongstr")
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		// Act
		// Remove a game with an invalid ID
		ResponseEntity<CartResponseDto> response2 = client.exchange(CartRequestEntity2, CartResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());

	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(7)
	public void testRemoveGameFromCartByValidId() {
		// Start with non-empty cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;
		RequestEntity<Void> CartRequestEntity1 = RequestEntity.get(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CartResponseDto> response = client.exchange(CartRequestEntity1, CartResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		List<GameResponseDto> games = cart.getGames();
		int gameId = games.get(0).getId();

		// Act
		// Remove a game with an invalid ID
		RequestEntity<Void> CartRequestEntity2 = RequestEntity.put(url + "?remove=" + gameId)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		// Act
		// Remove a game with an invalid ID
		ResponseEntity<CartResponseDto> response2 = client.exchange(CartRequestEntity2, CartResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.OK, response2.getStatusCode());
		assertNotNull(response2.getBody());
		CartResponseDto cart2 = response2.getBody();
		if (cart2 != null) {
			assertNotNull(cart2.getGames());
			assertEquals(0, cart2.getGames().size());
		}
	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(8)
	public void testClearCart() {
		// Arrange

		// Start with empty cart
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;

		// Add the game ID (of the game to be added to cart) to the request body
		CartRequestDto body = new CartRequestDto();
		body.setGameId(this.game.getId());
		RequestEntity<CartRequestDto> CartRequestEntity = RequestEntity.put(url)
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		// PUT request
		ResponseEntity<CartResponseDto> gameResponse = client.exchange(url, HttpMethod.PUT, CartRequestEntity,
				CartResponseDto.class);
		assertNotNull(gameResponse);

		// Act
		// Clear the cart
		RequestEntity<Void> req = RequestEntity.put(url + "?remove=all")
			.header("Authorization", customerAuth)
			.accept(MediaType.APPLICATION_JSON)
			.build();
		ResponseEntity<CartResponseDto> response = client.exchange(req, CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		CartResponseDto cart = response.getBody();
		if (cart != null) {
			assertNotNull(cart.getGames());
			assertEquals(0, cart.getGames().size());
		}
	}

}
