package ca.mcgill.ecse321.group12.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
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

import ca.mcgill.ecse321.group12.model.Cart;
import ca.mcgill.ecse321.group12.model.Customer;
import ca.mcgill.ecse321.group12.model.Game.Category;
import ca.mcgill.ecse321.group12.model.Game.Console;
import ca.mcgill.ecse321.group12.model.Game.GameStatus;
import ca.mcgill.ecse321.group12.repository.CartRepository;
import ca.mcgill.ecse321.group12.repository.CustomerRepository;
import ca.mcgill.ecse321.group12.repository.GameRepository;
import ca.mcgill.ecse321.group12.dto.CartRequestDto;
import ca.mcgill.ecse321.group12.dto.CartResponseDto;
import ca.mcgill.ecse321.group12.dto.CustomerRequestDto;
import ca.mcgill.ecse321.group12.dto.CustomerResponseDto;
import ca.mcgill.ecse321.group12.dto.GameRequestDto;
import ca.mcgill.ecse321.group12.dto.GameResponseDto;

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

	private Cart cart;

	private GameResponseDto game;

	@BeforeAll
	public void setup() {
		// Create (POST) a customer to use their cart for tests
		Customer customer = new Customer();
		CustomerRequestDto customerRequest = new CustomerRequestDto(customer);
		ResponseEntity<CustomerResponseDto> customerResponse = client.postForEntity("/customers", customerRequest,
				CustomerResponseDto.class);
		this.cart = customerResponse.getBody().getCart();
		this.validId = this.cart.getId();
		// Create (POST) a game to use it for tests
		GameRequestDto gameRequest = new GameRequestDto(Category.Action, Console.PC, 1, 1.0f, "Game Name...",
				"Game Description...", GameStatus.Archived);
		ResponseEntity<GameResponseDto> gameResponse = client.postForEntity("/games", gameRequest,
				GameResponseDto.class);
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
		String url = "/cart/" + this.validId;

		// Act
		ResponseEntity<CartResponseDto> response = client.getForEntity(url, CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		assertEquals(this.validId, cart.getId());
	}

	@Test
	@Order(2)
	public void testAddGameToCart() {
		// Start with an empty cart, add a game to the cart
		// Arrange
		String url = "/cart/" + this.validId;

		// Act
		CartRequestDto body = new CartRequestDto();
		body.setGameId(this.game.getId());
		RequestEntity<CartRequestDto> CartRequestEntity = RequestEntity.put(url)
			.accept(MediaType.APPLICATION_JSON)
			.body(body);
		ResponseEntity<CartResponseDto> response = client.exchange(url, HttpMethod.PUT, CartRequestEntity,
				CartResponseDto.class);

		// Assert
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		CartResponseDto cart = response.getBody();
		assertNotNull(cart);
		assertEquals(this.game.getCategory(), cart.getGames().get(0).getCategory());
		assertEquals(this.game.getConsole(), cart.getGames().get(0).getConsole());
		assertEquals(this.game.getInventory(), cart.getGames().get(0).getInventory());
		assertEquals(this.game.getPrice(), cart.getGames().get(0).getPrice());
		assertEquals(this.game.getName(), cart.getGames().get(0).getName());
		assertEquals(this.game.getDescription(), cart.getGames().get(0).getDescription());
		assertEquals(this.game.getStatus(), cart.getGames().get(0).getStatus());
		assertEquals(this.game.getId(), cart.getGames().get(0).getId());
	}

}
