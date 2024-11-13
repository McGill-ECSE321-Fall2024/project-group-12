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

import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;
import ca.mcgill.ecse321.group12.model.Customer;
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

	private CustomerResponseDto customer;

	private GameResponseDto game;

	@BeforeAll
	public void setup() {
		// Create (POST) a customer to use their cart for tests
		Customer customer = new Customer();
		CustomerRequestDto customerRequest = new CustomerRequestDto(customer);
		ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customers", customerRequest,
				CustomerResponseDto.class);
		// Save the response
		this.customer = customerResponse.getBody();
		// Create (POST) a game to use it for tests
		GameRequestDto gameRequest = new GameRequestDto(Category.Action, Console.PC, 1, 1.0f, "Game Name...",
				"Game Description...", GameStatus.Archived);
		ResponseEntity<GameResponseDto> gameResponse = client.postForEntity("/games", gameRequest,
				GameResponseDto.class);
		// Save the response
		this.game = gameResponse.getBody();
	}

	@AfterAll
	public void clearDatabase() {
		customerRepo.deleteAll();
		cartRepo.deleteAll();
		gameRepo.deleteAll();
	}

	@Test
	@Order(1)
	public void testFindCartByValidId() {
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;

		// Act
		ResponseEntity<CartResponseDto> response = client.getForEntity(url, CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		// Check that the cart found has the correct ID
		assertEquals(this.validId, cart.getId());
	}

	@Test
	@Order(2)
	public void testAddGameToCart() {
		// Start with an empty cart, add a game to the cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;

		// Act
		// Add the game ID (of the game to be added to cart) to the request body
		CartRequestDto body = new CartRequestDto();
		body.setGameId(this.game.getId());
		RequestEntity<CartRequestDto> CartRequestEntity = RequestEntity.put(url)
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
	@Order(3)
	public void testRemoveGameFromCartByInvalidId() {
		// Start with non-empty cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.GET, null, CartResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		List<GameResponseDto> games = cart.getGames();
		int wrongGameId = games.get(0).getId() + 1;

		// Act
		// Remove a game with an invalid ID
		ResponseEntity<CartResponseDto> response2 = client.exchange(url + "?remove=" + wrongGameId, HttpMethod.PUT,
				null, CartResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());

	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(4)
	public void testRemoveGameFromCartByInvalidString() {
		// Start with non-empty cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.GET, null, CartResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Act
		// Remove a game with an invalid ID
		ResponseEntity<CartResponseDto> response2 = client.exchange(url + "?remove=" + "wrongstr", HttpMethod.PUT, null,
				CartResponseDto.class);

		// Assert
		assertNotNull(response2);
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());

	}

	/**
	 * @author Julien Heng
	 */
	@Test
	@Order(5)
	public void testRemoveGameFromCartByValidId() {
		// Start with non-empty cart
		// Arrange
		this.validId = this.customer.getCart().getId();
		String url = "/cart/" + this.validId;
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.GET, null, CartResponseDto.class);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		List<GameResponseDto> games = cart.getGames();
		int gameId = games.get(0).getId();

		// Act
		// Remove a game with an invalid ID
		ResponseEntity<CartResponseDto> response2 = client.exchange(url + "?remove=" + gameId, HttpMethod.PUT, null,
				CartResponseDto.class);

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

}
